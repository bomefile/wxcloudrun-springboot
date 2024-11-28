package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.dto.ApiResponse;
import com.tencent.wxcloudrun.dto.TianApiResp;
import com.tencent.wxcloudrun.service.impl.TianApiService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TianApiController {

    @Resource
    private TianApiService tianApiService;

    @PostMapping(value = "/api/tiangou")
    public ApiResponse getTianGou()
    {
        TianApiResp tianGou = tianApiService.getTianGou();
        return ApiResponse.success(tianGou.getResult().getContent());
    }
}
