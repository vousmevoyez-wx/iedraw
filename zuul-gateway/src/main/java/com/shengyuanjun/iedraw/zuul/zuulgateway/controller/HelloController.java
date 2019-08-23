package com.shengyuanjun.iedraw.zuul.zuulgateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 你这个网关 不可以写 一个  Controller 做测试？
@RestController
@RequestMapping("/test2")
public class HelloController {

    @GetMapping("/test2")
    public String test2() {
        return "test2";
    }

}
