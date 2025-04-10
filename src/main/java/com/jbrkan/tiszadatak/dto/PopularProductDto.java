package com.jbrkan.tiszadatak.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@NoArgsConstructor
public class PopularProductDto {
    private String name;
    private BigDecimal averageRating;

    public PopularProductDto(String name, Double averageRating) {
        this.name = name;
        this.averageRating = averageRating == null
                ? null
                : BigDecimal.valueOf(averageRating).setScale(1, RoundingMode.HALF_UP);
    }
}
