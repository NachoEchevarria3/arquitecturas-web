package com.app.microparada.client;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    public FeignRequestInterceptor feignRequestInterceptor(HttpServletRequest request) {
        return new FeignRequestInterceptor(request);
    }
}
