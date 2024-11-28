package com.tencent.wxcloudrun.dto;

import lombok.Data;

@Data
public class TianApiResp {

    private String msg;
    private int code;
    private Result result;

    @Data
    public class Result{
        private String content;
    }
}
