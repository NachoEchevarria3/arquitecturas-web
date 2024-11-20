package com.app.micropago.client;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class FeignRequestInterceptor implements RequestInterceptor {
    private final ObjectFactory<HttpServletRequest> requestFactory;

    public FeignRequestInterceptor(ObjectFactory<HttpServletRequest> requestFactory) {
        this.requestFactory = requestFactory;
    }

    @Override
    public void apply(RequestTemplate template) {
        HttpServletRequest request = requestFactory.getObject();
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorization != null && authorization.startsWith("Bearer ")) {
            String token = authorization.split(" ")[1];
            template.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        }
    }
}
