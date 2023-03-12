package com.example.simplework.filter;

import com.example.simplework.constant.ResponseStatusCodeEnum;
import com.example.simplework.constant.TokenParams;
import com.example.simplework.factory.response.GeneralResponse;
import com.example.simplework.factory.response.ResponseFactory;
import com.example.simplework.factory.response.ResponseStatus;
import com.example.simplework.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@SuppressWarnings({"squid:S3776","squid:S1186"})
@Configuration
@Slf4j
@Order(4)
public class HeaderInfoFilter extends OncePerRequestFilter {

    @Autowired
    JwtService authUtilsService;
    @Autowired
    ResponseFactory responseFactory;

    public HeaderInfoFilter() {
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        if (requestWrapper.getRequestURI().contains("swagger") || requestWrapper.getRequestURI().contains("actuator") || requestWrapper.getRequestURI().contains("api-docs")) {
            chain.doFilter(requestWrapper, responseWrapper);
            responseWrapper.copyBodyToResponse();
            return;
        }

        String authorization = requestWrapper.getHeader("Authorization");
        if (authorization == null || authorization.length() == 0) {
            authorization = requestWrapper.getHeader("Authentication");
        }

        if (authorization != null && authorization.length() > 0) {
            if (!authorization.startsWith("Bearer ")) {
                sendError(responseWrapper, ResponseStatusCodeEnum.FORBIDDEN_INVALID_KEY);
                return;
            }
            authorization = authorization.substring(7);

            try {
                Claims claims = authUtilsService.getClaims(authorization);
                requestWrapper.setAttribute(Claims.AUDIENCE, claims.getAudience());
                requestWrapper.setAttribute(Claims.SUBJECT, claims.getSubject());
                requestWrapper.setAttribute(Claims.ID, claims.getId());
                requestWrapper.setAttribute(TokenParams.ROLES, claims.get(TokenParams.ROLES, ArrayList.class));
                requestWrapper.setAttribute(TokenParams.USERNAME, claims.get(TokenParams.USERNAME, String.class));
                chain.doFilter(requestWrapper, responseWrapper);
                responseWrapper.copyBodyToResponse();
            } catch (ExpiredJwtException e) {
                log.info("Exception token expire");
                sendError(responseWrapper, ResponseStatusCodeEnum.FORBIDDEN_TOKEN_EXPIRE);
            } catch (Exception e) {
                log.info("Unknown exception in validating token", e);
                sendError(responseWrapper, ResponseStatusCodeEnum.FORBIDDEN_INVALID_KEY);
            }
        } else {
            chain.doFilter(requestWrapper, responseWrapper);
            responseWrapper.copyBodyToResponse();
        }
    }

    private void sendError(ContentCachingResponseWrapper responseWrapper, ResponseStatusCodeEnum code) {
        try {
            responseWrapper.setStatus(HttpStatus.FORBIDDEN.value());
            responseWrapper.setContentType("application/json; charset=utf-8");
            GeneralResponse<Object> responseObject = new GeneralResponse<>();
            ResponseStatus responseStatus = new ResponseStatus(code.getCode(), true);
            responseObject.setStatus(responseStatus);
            new ObjectMapper().writeValue(responseWrapper.getWriter(), responseObject);
            responseWrapper.copyBodyToResponse();
        } catch (IOException e) {
            log.error("io exception in writing response", e);
        }
    }
}
