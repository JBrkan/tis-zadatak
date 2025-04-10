package com.jbrkan.tiszadatak.config.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;
import java.util.Optional;

record RequestInfo(String relativePath, String httpMethod, String className, String classMethod,
                   String methodParams) {
    static RequestInfo get(JoinPoint joinPoint, ObjectMapper objectMapper) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

        String relativePath = getRelativePath(request);
        String httpMethod = request.getMethod();

        String className = joinPoint.getTarget().getClass().getSimpleName();
        String classMethod = joinPoint.getSignature().getName();

        String methodParams = serializeToJson(joinPoint.getArgs(), objectMapper);

        return new RequestInfo(relativePath, httpMethod, className, classMethod, methodParams);
    }

    private static String getRelativePath(HttpServletRequest request) {
        final String queryParamsPartOfUrl =
                Optional.ofNullable(request.getQueryString()).map(queryString -> "?" + queryString).orElse("");
        return request.getRequestURI() + queryParamsPartOfUrl;
    }

    private static String serializeToJson(Object[] params, ObjectMapper objectMapper) {
        try {
            return objectMapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            return e.getMessage();
        }
    }
}
