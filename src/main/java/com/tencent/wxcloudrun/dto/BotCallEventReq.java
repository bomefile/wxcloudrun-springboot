package com.tencent.wxcloudrun.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BotCallEventReq {

  @JsonProperty("id")
  private String id;

  @JsonProperty("op")
  private Integer op;

  @JsonProperty("d")
  private SignCheckRequest.D d;

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class D {
    @JsonProperty("plain_token")
    private String plainToken;

    @JsonProperty("event_ts")
    private String eventTs;
  }

  @JsonProperty("s")
  private Integer s;

  @JsonProperty("t")
  private String t;
}