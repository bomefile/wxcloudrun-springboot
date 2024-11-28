package com.tencent.wxcloudrun.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TianApiConfig {

    @Value("${config.tianapi.apikey}")
    private String tianApikey;

    @Value("${config.tianapi.common-url}")
    private String tianApiUrl;
}
