package com.tencent.wxcloudrun.controller;


import com.tencent.wxcloudrun.config.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HelloController {

    @RequestMapping("/hello")
    public ApiResponse hello() {
        return ApiResponse.ok("hello world");
    }
}