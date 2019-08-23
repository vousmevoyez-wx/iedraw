package com.shengyuanjun.iedraw.client;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;


@Component
public class RedisClientFallbackFactory implements FallbackFactory<RedisClient> {
    @Override
    public RedisClient create(Throwable throwable) {
        return new RedisClient() {
            @Override
            public String get(String key) {
                return "哎呦redis，断了";
            }

            @Override
            public void set(String key, String value) {
                System.out.println("..........");
            }
        };
    }
}