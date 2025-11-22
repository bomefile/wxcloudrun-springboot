package com.tencent.wxcloudrun.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BotCallEventResp {

  @JsonProperty("id")
  private String id;

  @JsonProperty("op")
  private Integer op;

  @JsonProperty("d")
  private JsonNode d;

  @JsonProperty("s")
  private Integer s;

  @JsonProperty("t")
  private String t;
}