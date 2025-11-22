package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.dto.SignCheckResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SignServiceTest {

    @Test
    public void testCheck_GeneratesSignatureAndEchoesPlainToken() {
        SignService service = new SignService();
        String eventTs = "1725442341";
        String plainToken = "Arq0D5A61EgUu4OxUvOp";

        SignCheckResponse rsp = service.check(eventTs, plainToken);

        Assertions.assertNotNull(rsp);
        Assertions.assertEquals(plainToken, rsp.getPlainToken());
        Assertions.assertNotNull(rsp.getSignature());
        Assertions.assertEquals(128, rsp.getSignature().length());
        Assertions.assertTrue(rsp.getSignature().matches("^[0-9a-f]{128}$"));
    }

    @Test
    public void testCheck_DeterministicSignatureForSameInputs() {
        SignService service = new SignService();
        String eventTs = "1725442341";
        String plainToken = "Arq0D5A61EgUu4OxUvOp";

        SignCheckResponse rsp1 = service.check(eventTs, plainToken);
        SignCheckResponse rsp2 = service.check(eventTs, plainToken);

        Assertions.assertNotNull(rsp1);
        Assertions.assertNotNull(rsp2);
        Assertions.assertEquals(rsp1.getSignature(), rsp2.getSignature());
    }
}