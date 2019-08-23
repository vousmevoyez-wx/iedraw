package com.shengyuanjun.iedraw.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//feign的负载均衡 和断路器
@FeignClient(value = "DRAW-COMMON",fallbackFactory = RedisClientFallbackFactory.class)
public interface RedisClient {

    @RequestMapping(value = "/redis",method = RequestMethod.GET)
    public String get(@RequestParam("key") String key);

    @RequestMapping(value = "/redis",method = RequestMethod.POST)
    public void set(@RequestParam("key") String key, @RequestParam("value") String value);
}
