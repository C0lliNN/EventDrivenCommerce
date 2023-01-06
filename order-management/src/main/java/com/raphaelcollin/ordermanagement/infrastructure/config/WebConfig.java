package com.raphaelcollin.ordermanagement.infrastructure.config;

import com.raphaelcollin.ordermanagement.infrastructure.http.CorrelationIdInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new CorrelationIdInterceptor());
    }
}
