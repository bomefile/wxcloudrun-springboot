package com.tencent.wxcloudrun.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignCheckResponse {

  @JsonProperty("plain_token")
  public String plainToken;
  @JsonProperty("signature")
  public String signature;

}
