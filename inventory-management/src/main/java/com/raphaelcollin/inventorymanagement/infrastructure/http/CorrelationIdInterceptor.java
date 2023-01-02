package com.raphaelcollin.inventorymanagement.infrastructure.http;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
@Component
public class CorrelationIdInterceptor implements HandlerInterceptor {
    public final static String CORRELATION_ID_NAME = "correlation-id";

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        final String correlationId = getOrGenerateCorrelationId(request);

        log.info("New correlation id: {}", correlationId);

        MDC.put(CORRELATION_ID_NAME, correlationId);
        request.setAttribute(CORRELATION_ID_NAME, correlationId);
        return true;
    }

    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex) throws Exception {
        MDC.remove(CORRELATION_ID_NAME);
    }

    private String getOrGenerateCorrelationId(final HttpServletRequest request) {
        final String correlationId = request.getHeader(CORRELATION_ID_NAME);
        if(correlationId == null || correlationId.isEmpty()) {
            return UUID.randomUUID().toString();
        }
        return correlationId;
    }
}
