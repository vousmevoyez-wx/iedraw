package com.shengyuanjun.iedraw.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 资源映射路径,主要是配置二维码文件保存路径
 */
@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/gop/**","/goh/**","/image/**","/gops/**").addResourceLocations("D://gop/","D://images/goh/","file:D://images/","file:D://gop/");
    }
}