package com.shengyuanjun.iedraw.handlers;

/**
 * @program: gzher
 * @description:
 * @author: gq544
 * @create: 2019-08-04 16:31
 */
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 自定义的图片路径
 * @author pzr
 *
 */
@Configuration
public class MyImageAddr extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //addResourceHandler中的是访问路径，可以修改为其他的字符串
        //addResourceLocations中的是实际路径
        registry.addResourceHandler("/gop/**","/goh/**","/image/**","/gops/**").addResourceLocations("/user/jxb/gop/","/user/jxb/goh/","file:/user/jxb/goh/","file:/user/jxb/gop/");
//        registry.addResourceHandler("/gop/**","/goh/**","/image/**","/gops/**").addResourceLocations("D://gop/","D://images/goh/","file:D://images/","file:D://gop/");
        super.addResourceHandlers(registry);
    }
}