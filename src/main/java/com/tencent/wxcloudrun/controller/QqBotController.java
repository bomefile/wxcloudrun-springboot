package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.BotCallEventReq;
import com.tencent.wxcloudrun.dto.SignCheckRequest;
import com.tencent.wxcloudrun.dto.SignCheckResponse;
import com.tencent.wxcloudrun.service.SignService;
import com.tencent.wxcloudrun.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/api/bot")
public class QqBotController {

    @Resource
    private SignService signService;

    @Resource
    private TokenService tokenService;

    @PostMapping("/callback/check")
    public SignCheckResponse callbackCheck(@RequestBody BotCallEventReq botCallEventReq) {
        log.info("[callbackCheck]botCallEventReq: botCallEventReq={}", botCallEventReq);
        SignCheckResponse rsp = signService.check(botCallEventReq.getId(), botCallEventReq.getD().getPlainToken());
        log.info("[callbackCheck]response: signature_len={}", rsp.getSignature() == null ? 0 : rsp.getSignature().length());
        return rsp;
    }

    @GetMapping("token/get")
    public String getToken() {
        return tokenService.getToken();
    }

}
