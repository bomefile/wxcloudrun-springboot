package com.tencent.wxcloudrun.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.tencent.wxcloudrun.config.Constants;
import com.tencent.wxcloudrun.dto.AccessTokenResp;
import com.tencent.wxcloudrun.util.RestTemplateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TokenService {

    public String getToken() {
        HttpHeaders headers = new HttpHeaders();
        String payload = "{\"appId\":\"" + Constants.appId + "\",\"clientSecret\":\"" + Constants.secret + "\"}";
        log.info("getToken start url={}, appId={}, payload_len={}", Constants.token_url, Constants.appId, payload.length());
        String resp = RestTemplateUtil.postFormByJson(Constants.token_url, payload, headers, false);
        log.info("getToken response resp={}", resp);
        if (StrUtil.isEmpty(resp)) {
            log.warn("getToken empty response");
            return resp;
        }
        log.info("getToken parsing response");
        AccessTokenResp accessTokenResp = JSONUtil.toBean(resp, AccessTokenResp.class);
        String token = accessTokenResp.getAccessToken();
        int tokenLen = token == null ? 0 : token.length();
        String head = token == null ? null : (token.length() >= 6 ? token.substring(0, 6) : token);
        String tail = token == null ? null : (token.length() >= 6 ? token.substring(token.length() - 6) : token);
        log.info("getToken success token_len={}, head={}, tail={}", tokenLen, head, tail);
        return token;
    }
}
