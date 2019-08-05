package com.shengyuanjun.iedraw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2
@MapperScan("com.shengyuanjun.iedraw.mapper")
@EnableFeignClients(basePackages = "com.shengyuanjun.iedraw.client")
public class DrawServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DrawServiceApplication.class, args);
    }
}
