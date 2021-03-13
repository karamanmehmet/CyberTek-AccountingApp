package com.cybertek.accounting.configuration;

import com.cybertek.accounting.dto.JwtResponse;
import com.cybertek.accounting.dto.LoginRequest;
import com.cybertek.accounting.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class AuthSuccessHandler implements AuthenticationSuccessHandler {


    private final HttpSession httpSession;
    private final TokenService tokenService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());


        String username= httpServletRequest.getParameter("txtUsername");
        String password= httpServletRequest.getParameter("txtPassword");


        //after success login JWT token is creating and adding it into cookie
        LoginRequest loginRequest = new LoginRequest(username,password);

        JwtResponse tokenResponse = tokenService.authenticateUser(loginRequest);

        httpSession.setAttribute("Authorization", tokenResponse.getType()+" "+tokenResponse.getToken());


        if(roles.contains("ROLE_ROOT")){
            httpServletResponse.sendRedirect("/company/list");
        }
        else {
            httpServletResponse.sendRedirect("/main");
        }

    }
}
