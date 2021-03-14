package com.cybertek.accounting.implementation;

import com.cybertek.accounting.dto.Rates;
import com.cybertek.accounting.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpSession;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private WebClient webClient;
    private HttpSession httpSession;


    private static final String RATE_URI="/rates";

    public ExchangeRateServiceImpl(@Qualifier("restService-WebClient") WebClient webClient,HttpSession httpSession){
        this.webClient = webClient;
        this.httpSession = httpSession;
    }

    @Override
    public Rates exchangeRates() {
        return webClient
                .get()
                .uri(RATE_URI)
                .header("Authorization",httpSession.getAttribute("Authorization").toString())
                .retrieve()
                .bodyToMono(Rates.class)
                .block();
    }
}
