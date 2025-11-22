package com.tencent.wxcloudrun.service;

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

    private static final long appId = 11111111;
    private static final String secret = "DG5g3B4j9X2KOErG";


    public SignCheckResponse check(String eventTs, String plainToken) {
        String seedStr = secret;
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

        String signature;
        try {
            Ed25519Signer signer = new Ed25519Signer();
            signer.init(true, privateKey);
            byte[] msgBytes = msg.toByteArray();
            signer.update(msgBytes, 0, msgBytes.length);
            byte[] sig = signer.generateSignature();
            signature = toHex(sig);
        } catch (Exception e) {
            log.error("generate signature failed:", e);
            return null;
        }

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
