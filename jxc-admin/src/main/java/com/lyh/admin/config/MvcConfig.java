package com.lyh.admin.config;

import com.lyh.admin.interceptors.NoLoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    /**
     * 定义拦截器
     * @return
     */
    @Bean
    public NoLoginInterceptor noLoginInterceptor(){
          return new NoLoginInterceptor();
    }
    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(noLoginInterceptor())
        .addPathPatterns("/**")
        .excludePathPatterns("/index","/t-user/login","/css/**","/error/**","/images/**","/js/**","/lib/**");
    }
}
