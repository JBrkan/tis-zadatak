package com.jbrkan.tiszadatak.service;

import com.jbrkan.tiszadatak.dto.ExchangeRateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Currency;

@Service
@RequiredArgsConstructor
public class HNBApi {

    private static final URI HNB_API_URL = URI.create("https://api.hnb.hr/tecajn-eur/v3");
    private final RestTemplate restTemplate;

    public ExchangeRateDto getExchangeRateForCurrency(Currency currency) {
        String url = UriComponentsBuilder.fromUri(HNB_API_URL)
                .queryParam("valuta", currency.getCurrencyCode())
                .toUriString();

        ExchangeRateDto[] exchangeRateArray = restTemplate.getForObject(url, ExchangeRateDto[].class);

        if (exchangeRateArray == null || exchangeRateArray.length == 0) {
            throw new ResourceAccessException("HNB API returned empty array");
        }

        return exchangeRateArray[0];
    }
}
