package com.neu.config;

import com.neu.interceptor.JwtValidateInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author xiaojin
 * @Date 2023/5/26 10:19
 */
@Configuration
public class MyInterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private JwtValidateInterceptor jwtValidateInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(jwtValidateInterceptor);

        registration.addPathPatterns("/**").excludePathPatterns(
                "/supervisor/login",
                "/admin/login",
                "/admin/info",
                "/supervisor/info",
                "/staff/login",
                "/staff/info",
                "/supervisor/register"
        );



    }
}
