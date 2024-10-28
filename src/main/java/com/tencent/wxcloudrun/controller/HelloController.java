package com.tencent.wxcloudrun.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * index控制器
 */
@RestController
public class HelloController {

  /**
   * 主页页面
   * @return API response html
   */
  @GetMapping("/hello")
  public String index() {
    return "hello,world";
  }

}
