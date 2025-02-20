package com.example.nbcnewsfeed.auth.config;

import com.example.nbcnewsfeed.auth.jwt.filter.JwtFilter;
import com.example.nbcnewsfeed.auth.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FilterConfig : JWT 필터를 등록하는 설정 클래스
 * @Bean : JwtFilter 클래스를 Filter로 등록해서 Bean으로 등록
 * - FilterRegistrationBean : 필터의 등록과 초기화 매개변수를 설정하게 해주는 Spring 유틸리티 클래스
 */
@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    private final JwtUtil jwtUtil;

    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilter() {
        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtFilter(jwtUtil)); // JwtUtil에서 만든 JWT 검증하는 JwtFilter 클래스 필터로 등록
        registrationBean.addUrlPatterns("/*"); // 해당 필터를 거치는 URL 설정(여기서는 모든 URL 경로)

        return registrationBean;
    }
}