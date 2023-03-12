package com.example.simplework.filter;

import com.example.simplework.config.AppConfig;
import com.example.simplework.factory.response.GeneralResponse;
import com.example.simplework.factory.response.ResponseStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SuppressWarnings({"squid:S3740", "squid:S1874", "java:S125"})
@Configuration
@Slf4j
@Order(1)
public class LogCorrelationFilter extends OncePerRequestFilter {

    final ObjectMapper om;
    private final AppConfig appConfig;

    public LogCorrelationFilter(AppConfig appConfig, ObjectMapper om) {
        this.appConfig = appConfig;
        this.om = om;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        long time = System.currentTimeMillis();

        request = new RequestWrapper(request);
        // String requestBody = ((RequestWrapper) request).getBody() bo luu staff log

        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        String uri = request.getRequestURI();
        String method = request.getMethod();

        chain.doFilter(request, responseWrapper);

        if (!uri.startsWith("/actuator")) {

            String responseBody = new String(responseWrapper.getContentAsByteArray());

            ResponseStatus status = getApplicationErrorCode(responseBody);

            String errorType;
            if (status != null) {
                if ("00".equalsIgnoreCase(status.getCode())) {
                    errorType = "Success";
                } else if ("500".equals(status.getCode())) {
                    errorType = "ErrorServer";
                } else {
                    errorType = "ErrorClient";
                }
                log.info("REPORT|{}|{}|{}|{}|{}|{}|{}|{}", appConfig.getApplicationShortName(), method, uri, response.getStatus(), errorType,
                        // thanh cong: 0
                        // client error: 1
                        // server error: 2
                        status.getCode(), System.currentTimeMillis() - time, status.getMessage());
                // writeToTransactionLog(uri, method, requestBody, responseBody, correlationId, businessCode,
                // response.getStatus() + "") bo luu staff log
            }
        }
        responseWrapper.copyBodyToResponse();

        log.info("{}: {} ms ", request.getRequestURI(), System.currentTimeMillis() - time);
        ThreadContext.clearAll();
    }

    private ResponseStatus getApplicationErrorCode(String responseBody) {
        try {
            return om.readValue(responseBody, GeneralResponse.class).getStatus();
        } catch (Exception e) {
            return null;
        }
    }
}
