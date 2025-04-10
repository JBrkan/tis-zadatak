package com.jbrkan.tiszadatak.dto;

import java.math.BigDecimal;

public record ProductViewDto(
        Long id,
        String code,
        String name,
        BigDecimal priceEur,
        BigDecimal priceUsd,
        String description
) {
}
