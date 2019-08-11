package com.shengyuanjun.iedraw.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 资源映射路径,主要是配置二维码文件保存路径
 */
@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/gop/**","/goh/**","/image/**","/gops/**").addResourceLocations("/user/jxb/gop/","/user/jxb/goh/","file:/user/jxb/goh/","file:/user/jxb/gop/");
    }
}


