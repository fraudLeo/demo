package com.leo.config;

import com.leo.config.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description: 注册驱动
 * @version: 1.0
 * @author: Leo
 * @eamil: fraudLeo1@Gmail.com
 * @date:2022/10/29 16:36
 */
@Configuration
public class InterceptionConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())//这俩倘若直接new JwtInterceptor(),是没有进入到容器托管的,所以不能这么写
                .addPathPatterns("/**") //拦截所有请求,通过判断tooken是否合法来决定是否需要登陆
                .excludePathPatterns("/user/login","/user/register","/**/export","/**/import","/file/**");//放行,不进行验证
    }
    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }
}
