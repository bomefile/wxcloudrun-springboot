package com.tencent.wxcloudrun.service.impl;

import com.alibaba.fastjson2.JSON;
import com.tencent.wxcloudrun.dto.TianApiResp;
import com.tencent.wxcloudrun.util.OkHttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TianApiService {

    @Value("${config.tianapi.apikey}")
    private String tianApikey;

    @Value("${config.tianapi.common-url}")
    private String tianApiUrl;

    private final String tiangouUrl = "/tiangou/index";

    private Map<String, String> buildPostForm() {
        Map<String, String> params = new HashMap();
        params.put("key", tianApikey);
        return params;
    }
    public TianApiResp getTianGou() {
        String url = tianApiUrl + tiangouUrl;
        String result = OkHttpClientUtil.doPost(url, buildPostForm(), null);
        return JSON.parseObject(result, TianApiResp.class);
    }
}
