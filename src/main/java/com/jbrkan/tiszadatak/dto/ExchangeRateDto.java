package com.jbrkan.tiszadatak.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record ExchangeRateDto(
        String datumPrimjene,
        String drzava,
        String valuta,
        String sifraValute,
        BigDecimal kupovniTecaj,
        BigDecimal srednjiTecaj,
        BigDecimal prodajniTecaj) {

    @JsonCreator
    public ExchangeRateDto(
            @JsonProperty("datum_primjene") String datumPrimjene,
            @JsonProperty("drzava") String drzava,
            @JsonProperty("valuta") String valuta,
            @JsonProperty("sifra_valute") String sifraValute,
            @JsonProperty("kupovni_tecaj") String kupovniTecaj,
            @JsonProperty("srednji_tecaj") String srednjiTecaj,
            @JsonProperty("prodajni_tecaj") String prodajniTecaj) {
        this(
                datumPrimjene,
                drzava,
                valuta,
                sifraValute,
                parseDecimal(kupovniTecaj),
                parseDecimal(srednjiTecaj),
                parseDecimal(prodajniTecaj)
        );
    }

    private static BigDecimal parseDecimal(String raw) {
        if (raw == null || raw.isBlank()) return null;
        try {
            return new BigDecimal(raw.replace(",", "."));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid decimal format: " + raw, e);
        }
    }
}
