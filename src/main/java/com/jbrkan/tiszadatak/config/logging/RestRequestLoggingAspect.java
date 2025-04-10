package com.jbrkan.tiszadatak.config.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RestRequestLoggingAspect {
    private final ObjectMapper objectMapper;

    @Before("@annotation(requestLogger)")
    public void log(JoinPoint joinPoint, RequestLogger requestLogger) {
        log.info("Request info: {}, {}", RequestInfo.get(joinPoint, objectMapper),
                requestLogger.message());
    }
}

