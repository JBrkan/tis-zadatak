package com.jbrkan.tiszadatak.service;

import com.jbrkan.tiszadatak.dto.ExchangeRateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Currency;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HNBApiTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private HNBApi hnbApi;

    private Currency usdCurrency;

    @BeforeEach
    void setUp() {
        usdCurrency = Currency.getInstance("USD");
    }

    @Test
    void getExchangeRateForCurrency_successfulResponse_returnsFirstElement() {
        ExchangeRateDto expectedRate = new ExchangeRateDto("2024-05-02",
                "HR",
                "dollar",
                "USD",
                new BigDecimal("1.100023"),
                new BigDecimal("1.100023"),
                new BigDecimal("1.100023")
        );

        ExchangeRateDto[] mockResponse = new ExchangeRateDto[]{expectedRate};

        when(restTemplate.getForObject(
                "https://api.hnb.hr/tecajn-eur/v3?valuta=USD",
                ExchangeRateDto[].class
        )).thenReturn(mockResponse);

        ExchangeRateDto result = hnbApi.getExchangeRateForCurrency(usdCurrency);

        assertNotNull(result);
        assertEquals(expectedRate, result);
        verify(restTemplate).getForObject(
                "https://api.hnb.hr/tecajn-eur/v3?valuta=USD",
                ExchangeRateDto[].class
        );
    }

    @Test
    void getExchangeRateForCurrency_nullResponse_throwsResourceAccessException() {
        when(restTemplate.getForObject(
                "https://api.hnb.hr/tecajn-eur/v3?valuta=USD",
                ExchangeRateDto[].class
        )).thenReturn(null);

        assertThatThrownBy(() -> hnbApi.getExchangeRateForCurrency(usdCurrency))
        .isInstanceOf(ResourceAccessException.class)
                .hasMessage("HNB API returned empty array");

        verify(restTemplate).getForObject(
                "https://api.hnb.hr/tecajn-eur/v3?valuta=USD",
                ExchangeRateDto[].class
        );
    }

    @Test
    void getExchangeRateForCurrency_emptyArrayResponse_throwsResourceAccessException() {
        ExchangeRateDto[] emptyArray = new ExchangeRateDto[]{};
        when(restTemplate.getForObject(
                "https://api.hnb.hr/tecajn-eur/v3?valuta=USD",
                ExchangeRateDto[].class
        )).thenReturn(emptyArray);

        assertThatThrownBy(() -> hnbApi.getExchangeRateForCurrency(usdCurrency))
                .isInstanceOf(ResourceAccessException.class)
                .hasMessage("HNB API returned empty array");

        verify(restTemplate).getForObject(
                "https://api.hnb.hr/tecajn-eur/v3?valuta=USD",
                ExchangeRateDto[].class
        );
    }

    @Test
    void getExchangeRateForCurrency_restTemplateThrowsException_propagatesException() {
        RuntimeException mockException = new ResourceAccessException("Network error");
        when(restTemplate.getForObject(
                "https://api.hnb.hr/tecajn-eur/v3?valuta=USD",
                ExchangeRateDto[].class
        )).thenThrow(mockException);

        assertThatThrownBy(() -> hnbApi.getExchangeRateForCurrency(usdCurrency))
                .isInstanceOf(ResourceAccessException.class)
                .hasMessage("Network error");

        verify(restTemplate).getForObject(
                "https://api.hnb.hr/tecajn-eur/v3?valuta=USD",
                ExchangeRateDto[].class
        );
    }
}
