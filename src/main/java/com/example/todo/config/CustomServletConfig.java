package com.example.todo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomServletConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // CORS 설정을 적용할 URL
                .allowedOrigins("*") // 자원 공유 허락할 origin 설정(origin: 프로토콜, ip주소, 포트번호)
                .allowedMethods("HEAD", "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS") // 요청 방식
                .maxAge(300) // 기존에 허락한 요청 정보를 기억할 시간 설정 (CORS 캐싱 시간, 안 적어도 상관 없음)
                .allowedHeaders("Authorization", "Cache-Control","Content-Type"); // 외부에서 들어오는 요청을 허락할 헤더 정보 종류
    }
}
