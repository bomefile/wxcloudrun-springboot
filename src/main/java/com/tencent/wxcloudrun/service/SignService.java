package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.config.Constants;
import com.tencent.wxcloudrun.dto.SignCheckResponse;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters;
import org.bouncycastle.crypto.signers.Ed25519Signer;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class SignService {

    private static final int SEED_SIZE = 32;


    public SignCheckResponse check(String eventTs, String plainToken) {
        log.info("sign check start: event_ts={}, plain_token_len={}", eventTs, plainToken == null ? null : plainToken.length());
        String seedStr = Constants.secret;
        while (seedStr.length() < SEED_SIZE) {
            seedStr = seedStr + seedStr;
        }
        seedStr = seedStr.substring(0, SEED_SIZE);
        byte[] seed = seedStr.getBytes(StandardCharsets.UTF_8);

        Ed25519PrivateKeyParameters privateKey;
        try {
            privateKey = new Ed25519PrivateKeyParameters(seed, 0);
        } catch (Exception e) {
            log.error("ed25519 generate key failed:", e);
            return null;
        }

        ByteArrayOutputStream msg = new ByteArrayOutputStream();
        try {
            msg.write(stringBytes(eventTs));
            msg.write(stringBytes(plainToken));
        } catch (IOException e) {
            log.error("generate signature failed:", e);
            return null;
        }
        byte[] msgBytes = msg.toByteArray();
        log.info("sign check message size={} bytes", msgBytes.length);

        String signature;
        try {
            Ed25519Signer signer = new Ed25519Signer();
            signer.init(true, privateKey);
            signer.update(msgBytes, 0, msgBytes.length);
            byte[] sig = signer.generateSignature();
            signature = toHex(sig);
        } catch (Exception e) {
            log.error("generate signature failed:", e);
            return null;
        }
        int hexLen = signature == null ? 0 : signature.length();
        String head = signature == null ? null : (signature.length() >= 8 ? signature.substring(0, 8) : signature);
        String tail = signature == null ? null : (signature.length() >= 8 ? signature.substring(signature.length() - 8) : signature);
        log.info("sign check success: signature_len={}, head={}, tail={}", hexLen, head, tail);

        return SignCheckResponse.builder().signature(signature).plainToken(plainToken).build();
    }

    private static byte[] stringBytes(String s) {
        return s == null ? new byte[0] : s.getBytes(StandardCharsets.UTF_8);
    }

    private static String toHex(byte[] bytes) {
        char[] hexArray = "0123456789abcdef".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
