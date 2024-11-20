package com.app.micropago.client;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    public FeignRequestInterceptor feignRequestInterceptor(ObjectFactory<HttpServletRequest> requestFactory) {
        return new FeignRequestInterceptor(requestFactory);
    }
}
