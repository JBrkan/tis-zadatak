package com.jbrkan.tiszadatak.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductCreateDto(
        @NotBlank(message = "Code must not be empty")
        @Size(min = 15, max = 15, message = "Code must be exactly 15 characters long")
        String code,
        @NotBlank(message = "Name must not be empty")
        String name,
        @DecimalMin(value = "0.00", message = "Price EUR must be a positive value")
        @Digits(integer = 17, fraction = 2, message = "Price EUR must have up to 2 decimal places")
        BigDecimal priceEur,
        String description
) {
}
