package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.dto.SignCheckResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SignServiceTest {


    @Test
    public void testCheckWithSecret_DifferentSecretProducesDifferentSignature() {
        SignService service = new SignService();
        String eventTs = "1725442341";
        String plainToken = "Arq0D5A61EgUu4OxUvOp";
        String secret2 = "DG5g3B4j9X2KOErG";

        SignCheckResponse b = service.check(eventTs, plainToken, secret2);

        Assertions.assertNotEquals("87befc99c42c651b3aac0278e71ada338433ae26fcb24307bdc5ad38c1adc2d01bcfcadc0842edac85e85205028a1132afe09280305f13aa6909ffc2d652c706", b.getSignature());
    }
}