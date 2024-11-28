package com.tencent.wxcloudrun.service.impl;

import com.alibaba.fastjson2.JSON;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TianApiServiceTest {

    @Resource
    private TianApiService tianApiService;

    @Test
    void getTianGou() {
        System.out.println(JSON.toJSONString(tianApiService.getTianGou()));
    }
}