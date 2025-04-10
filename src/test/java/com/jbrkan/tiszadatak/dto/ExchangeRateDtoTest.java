package com.jbrkan.tiszadatak.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ExchangeRateDtoTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldDeserializeProperlyFormattedJson() throws Exception {
        String json = """
            {
              "datum_primjene": "2025-04-10",
              "drzava": "Hrvatska",
              "valuta": "EUR",
              "sifra_valute": "978",
              "kupovni_tecaj": "7,500",
              "srednji_tecaj": "7,520",
              "prodajni_tecaj": "7,540"
            }
        """;

        ExchangeRateDto dto = objectMapper.readValue(json, ExchangeRateDto.class);

        assertThat(dto.datumPrimjene()).isEqualTo("2025-04-10");
        assertThat(dto.drzava()).isEqualTo("Hrvatska");
        assertThat(dto.valuta()).isEqualTo("EUR");
        assertThat(dto.sifraValute()).isEqualTo("978");
        assertThat(dto.kupovniTecaj()).isEqualByComparingTo(BigDecimal.valueOf(7.5));
        assertThat(dto.srednjiTecaj()).isEqualByComparingTo(BigDecimal.valueOf(7.52));
        assertThat(dto.prodajniTecaj()).isEqualByComparingTo(BigDecimal.valueOf(7.54));
    }

    @Test
    void shouldHandleNullAndBlankStrings() throws Exception {
        String json = """
            {
              "datum_primjene": "2025-04-10",
              "drzava": "Narnia",
              "valuta": "NAR",
              "sifra_valute": "999",
              "kupovni_tecaj": "",
              "srednji_tecaj": null,
              "prodajni_tecaj": "8,000"
            }
        """;

        ExchangeRateDto dto = objectMapper.readValue(json, ExchangeRateDto.class);

        assertThat(dto.kupovniTecaj()).isNull();
        assertThat(dto.srednjiTecaj()).isNull();
        assertThat(dto.prodajniTecaj()).isEqualByComparingTo(BigDecimal.valueOf(8.0));
    }
}

