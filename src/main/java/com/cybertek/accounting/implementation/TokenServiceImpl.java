package com.cybertek.accounting.implementation;


import com.cybertek.accounting.dto.JwtResponse;
import com.cybertek.accounting.dto.LoginRequest;
import com.cybertek.accounting.service.TokenService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TokenServiceImpl implements TokenService {

	
	private WebClient webClient;
	private static final String SIGNIN_URI="/auth/token";

	

	public TokenServiceImpl(@Qualifier("restService-WebClient") WebClient webClient) {

		this.webClient = webClient;
	}
	
	
	
	@Override
	public JwtResponse authenticateUser(LoginRequest loginRequest) {


		return  webClient
				.post()
				.uri(SIGNIN_URI)
				.body(Mono.just(loginRequest), LoginRequest.class)
				.retrieve()
				.bodyToMono(JwtResponse.class)
				.block();
	}

}
