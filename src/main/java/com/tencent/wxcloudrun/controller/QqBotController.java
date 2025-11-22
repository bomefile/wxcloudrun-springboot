package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.BotCallEventResp;
import com.tencent.wxcloudrun.dto.SignCheckRequest;
import com.tencent.wxcloudrun.dto.SignCheckResponse;
import com.tencent.wxcloudrun.service.SignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/api/bot")
public class QqBotController {

    @Resource
    private SignService signService;

    @PostMapping("/callback/check")
    public ApiResponse callbackCheck(@RequestBody SignCheckRequest signCheckRequest) {
        log.info("sign check request body: op={}, event_ts={}, plain_token={}",
                signCheckRequest.getOp(),
                signCheckRequest.getEventTs(),
                signCheckRequest.getPlainToken());
        SignCheckResponse rsp = signService.check(signCheckRequest.getEventTs(), signCheckRequest.getPlainToken());
        log.info("sign check response: signature_len={}", rsp.getSignature() == null ? 0 : rsp.getSignature().length());
        return ApiResponse.ok(rsp);
    }

    @PostMapping("/callback/msg")
    public void callbackMsg(@RequestBody BotCallEventResp botCallEventResp) {
        log.info("callbackMsg: botCallEventResp={}", botCallEventResp);
    }
}
