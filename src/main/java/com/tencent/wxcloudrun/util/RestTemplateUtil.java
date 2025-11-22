package com.tencent.wxcloudrun.util;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName RestTemplateUtil
 * @Description TODO
 * @Author shibo5
 * @Date 2023/4/20 下午4:30
 */
@Slf4j
public class RestTemplateUtil {

    public final static int DEFAULT_TIMEOUT_MS = 500;


    // 全局连接池配置
    private static PoolingHttpClientConnectionManager CONNECTION_MANAGER = new PoolingHttpClientConnectionManager();

    static {
        // 最大连接数
        CONNECTION_MANAGER.setMaxTotal(200);
        // 每个路由的最大连接数
        CONNECTION_MANAGER.setDefaultMaxPerRoute(50);
    }

    /**
     * 创建 HttpComponentsClientHttpRequestFactory
     * @param connectTimeout 连接超时时间
     * @param readTimeout 读取超时时间
     * @param connectionRequestTimeout 获取连接的超时时间
     * @return ClientHttpRequestFactory
     */
    private static ClientHttpRequestFactory createRequestFactory(int connectTimeout, int readTimeout, int connectionRequestTimeout) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(readTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .build();

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(CONNECTION_MANAGER)
                .setDefaultRequestConfig(requestConfig)
                .build();

        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

    /**
     * 动态创建带超时的 RestTemplate
     * @param connectTimeout 连接超时时间（毫秒）
     * @param readTimeout 读取超时时间（毫秒）
     * @param connectionRequestTimeout 获取连接超时时间（毫秒）
     * @return RestTemplate 实例
     */
    public static RestTemplate getRestTemplate(int connectTimeout, int readTimeout, int connectionRequestTimeout) {
        return new RestTemplate(createRequestFactory(connectTimeout, readTimeout, connectionRequestTimeout));
    }

    public static RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    public static String postForm(String url, Map<String, Object> params) {
        return postForm(url, params, false);
    }

    public static String postForm(String url, Map<String, Object> params, boolean isTimeout) {
        Integer time = isTimeout ? DEFAULT_TIMEOUT_MS : null;
        return postForm(url, params, time, time, time);
    }

    public static String postForm(String url, Map<String, Object> params, Integer connectTimeout, Integer readTimeout, Integer connectionRequestTimeout) {
        long startTime = System.currentTimeMillis();
        String res = "";
        try {
            RestTemplate restTemplate = null;
            if (ObjectUtils.allNotNull(connectTimeout, readTimeout, connectionRequestTimeout)) {
                restTemplate = getRestTemplate(connectTimeout, readTimeout, connectionRequestTimeout);
            } else {
                restTemplate = getRestTemplate();
            }

            HttpHeaders headers = new HttpHeaders();
            //请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            //封装参数，千万不要替换为Map与HashMap，否则参数无法传递
            MultiValueMap<String, Object> formMap = new LinkedMultiValueMap<>();
            for (String key : params.keySet()) {
                formMap.add(key, params.get(key));
            }
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(formMap, headers);
            //执行HTTP请求
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            //输出结果
            res = response.getBody();
        } catch (Exception e) {
            log.error("[RestTemplateUtil] postForm error url:{},param:{}", url, params, e);
        } finally {
            long endTime = System.currentTimeMillis();
            long timeCost = endTime - startTime;
            log.info("[RestTemplateUtil] postForm end url:{},param:{},res:{},time:{}", url, params, res, timeCost);
        }
        return res;
    }

    public static String postForm(RestTemplate restTemplate, String url, Map<String, Object> params) {
        long startTime = System.currentTimeMillis();
        String res = "";
        try {
            HttpHeaders headers = new HttpHeaders();
            //请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            //封装参数，千万不要替换为Map与HashMap，否则参数无法传递
            MultiValueMap<String, Object> formMap = new LinkedMultiValueMap<>();
            for (String key : params.keySet()) {
                formMap.add(key, params.get(key));
            }
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(formMap, headers);
            //执行HTTP请求
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            //输出结果
            res = response.getBody();
        } catch (Exception e) {
            log.error("RestTemplateUtil post error url:{},param:{}", url, params, e);
        } finally {
            long endTime = System.currentTimeMillis();
            long timeCost = endTime - startTime;
            log.info("RestTemplateUtil post end url:{},param:{},res:{},time:{}", url, params, res, timeCost);
        }
        return res;
    }

    public static String postForm(String url, String json, HttpHeaders headers) {
        long startTime = System.currentTimeMillis();
        String res = "";
        try {
            RestTemplate restTemplate = getRestTemplate();

            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            headers.add("Accept", MediaType.APPLICATION_JSON.toString());
            HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);
            log.info("RestTemplateUtil postForm start url:{},requestEntity:{}", url, JSONUtil.toJsonStr(requestEntity));
            //执行HTTP请求
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            //输出结果
            res = response.getBody();
        } catch (Exception e) {
            log.error("[RestTemplateUtil] postForm error url:{},param:{}", url, json, e);
        } finally {
            long endTime = System.currentTimeMillis();
            long timeCost = endTime - startTime;
            log.info("[RestTemplateUtil] postForm json end url:{},headers:{},param:{},res:{},time:{}", url, headers, json, res, timeCost);
        }
        return res;
    }

    public static String postFormByJson(String url, String json, HttpHeaders headers, boolean isTimeout) {
        Integer time = isTimeout ? DEFAULT_TIMEOUT_MS : null;
        return postFormByJson(url, json, headers, time, time, time);
    }

    public static String postFormByJson(String url, String json, HttpHeaders headers, Integer connectTimeout, Integer readTimeout, Integer connectionRequestTimeout) {
        long startTime = System.currentTimeMillis();
        String res = "";
        try {
            RestTemplate restTemplate = null;
            if (ObjectUtils.allNotNull(connectTimeout, readTimeout, connectionRequestTimeout)) {
                restTemplate = getRestTemplate(connectTimeout, readTimeout, connectionRequestTimeout);
            } else {
                restTemplate = getRestTemplate();
            }

            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            headers.add("Accept", MediaType.APPLICATION_JSON.toString());
            HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);
            //执行HTTP请求
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            //输出结果
            res = response.getBody();
        } catch (Exception e) {
            log.error("[RestTemplateUtil] postFormByJson error url:{},param:{}", url, json, e);
        } finally {
            long endTime = System.currentTimeMillis();
            long timeCost = endTime - startTime;
            log.info("[RestTemplateUtil] postFormByJson json end url:{},headers:{},param:{},res:{},time:{}", url, headers, json, res, timeCost);
        }
        return res;
    }

    public static String getUrlQuery(String url, Map<String, Object> params) {
        return getUrlQuery(url, params, false);
    }

    public static String getUrlQuery(String url, Map<String, Object> params, boolean isTimeout) {
        Integer time = isTimeout ? DEFAULT_TIMEOUT_MS : null;
        return getUrlQuery(url, params, time, time, time);
    }

    public static String getUrlQuery(String url, Map<String, Object> params, Integer connectTimeout, Integer readTimeout, Integer connectionRequestTimeout) {
        long startTime = System.currentTimeMillis();
        String res = "";
        try {
            RestTemplate restTemplate = null;
            if (ObjectUtils.allNotNull(connectTimeout, readTimeout, connectionRequestTimeout)) {
                restTemplate = getRestTemplate(connectTimeout, readTimeout, connectionRequestTimeout);
            } else {
                restTemplate = getRestTemplate();
            }

            String urlParams = getUrlParamsByMap(params);
            res = restTemplate.getForObject(url + "?" + urlParams, String.class);
        } catch (Exception e) {
            log.error("[RestTemplateUtil] getUrlQuery error url:{},param:{}", url, params, e);
        } finally {
            long endTime = System.currentTimeMillis();
            long timeCost = endTime - startTime;
            log.info("[RestTemplateUtil] getUrlQuery end url:{},param:{},res:{},time:{}", url, params, res, timeCost);
        }
        return res;
    }

    public static String getURIQuery(String url, Map<String, Object> params, boolean isTimeout) {
        Integer time = isTimeout ? DEFAULT_TIMEOUT_MS : null;
        return getURIQuery(url, params, time, time, time);
    }


    public static String getURIQuery(String url, Map<String, Object> params, Integer connectTimeout, Integer readTimeout, Integer connectionRequestTimeout) {
        long startTime = System.currentTimeMillis();
        String res = "";
        try {
            RestTemplate restTemplate = null;
            if (ObjectUtils.allNotNull(connectTimeout, readTimeout, connectionRequestTimeout)) {
                restTemplate = getRestTemplate(connectTimeout, readTimeout, connectionRequestTimeout);
            } else {
                restTemplate = getRestTemplate();
            }
            String urlParams = getUrlParamsByMapEncoded(params);
            URI uri = URI.create(url + "?" + urlParams);
            res = restTemplate.getForObject(uri, String.class);
        } catch (HttpClientErrorException e) {
            int statusCode = e.getRawStatusCode();
            String responseBody = e.getResponseBodyAsString();
            
            if (statusCode >= 400 && statusCode < 500) {
                log.warn("[RestTemplateUtil] Client error ({}), url:{}, params:{}, returning error response for upper layer processing", statusCode, url, params);
                res = responseBody;
            } else {
                log.error("[RestTemplateUtil] Server error ({}), url:{}, params:{}", statusCode, url, params, e);
                throw e;
            }
        } catch (Exception e) {
            // 其他异常（网络错误等）继续抛出
            log.error("[RestTemplateUtil] Request failed, url:{}, params:{}", url, params, e);
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            long timeCost = endTime - startTime;
            log.info("[RestTemplateUtil] getURIQuery end url:{}, params:{}, res:{}, time:{}", url, params, res, timeCost);
        }
        return res;
    }



    public static String getUrlParamsByMap(Map<String, Object> map) {
        if (map == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = StringUtils.substringBeforeLast(s, "&");
        }
        return s;
    }

    /**
     * 将Map转换为URL参数字符串（带URL编码）
     */
    public static String getUrlParamsByMapEncoded(Map<String, Object> map) {
        if (map == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            try {
                String key = java.net.URLEncoder.encode(entry.getKey(), "UTF-8");
                String value = java.net.URLEncoder.encode(String.valueOf(entry.getValue()), "UTF-8");
                sb.append(key + "=" + value);
                sb.append("&");
            } catch (java.io.UnsupportedEncodingException e) {
                log.error("[RestTemplateUtil] URL encoding error for key:{}, value:{}", entry.getKey(), entry.getValue(), e);
                // 如果编码失败，仍然使用原始值，但记录错误
                sb.append(entry.getKey() + "=" + entry.getValue());
                sb.append("&");
            }
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = StringUtils.substringBeforeLast(s, "&");
        }
        return s;
    }
}
