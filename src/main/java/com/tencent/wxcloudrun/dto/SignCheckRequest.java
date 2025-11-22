package com.tencent.wxcloudrun.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignCheckRequest {
    @JsonProperty("op")
    private Integer op;

    @JsonProperty("d")
    private D d;

    public String getEventTs() {
        return d == null ? null : d.getEventTs();
    }

    public String getPlainToken() {
        return d == null ? null : d.getPlainToken();
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class D {
        @JsonProperty("plain_token")
        private String plainToken;

        @JsonProperty("event_ts")
        private String eventTs;
    }
}
