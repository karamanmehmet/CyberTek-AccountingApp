package com.cybertek.accounting.service;


import com.cybertek.accounting.dto.JwtResponse;
import com.cybertek.accounting.dto.LoginRequest;

public interface TokenService {

	JwtResponse authenticateUser(LoginRequest loginRequest);
}
