package com.jbrkan.tiszadatak.advice;

import java.time.Instant;
import java.util.List;

public record ErrorResponse(
        Instant timestamp,
        List<String> errors
) {
    public ErrorResponse(List<String> errors) {
        this(Instant.now(), errors);
    }
}


