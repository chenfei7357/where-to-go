package com.chenfei.where.to.go.config;
/*
 * Created by chenfei on 2019/3/24 19:28
 */

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(
                new LoginInterceptor()
        ).addPathPatterns("/**");
    }

}
