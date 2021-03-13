package com.cybertek.accounting.configuration;

import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.dto.JwtResponse;
import com.cybertek.accounting.dto.LoginRequest;
import com.cybertek.accounting.dto.UserDto;
import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.User;
import com.cybertek.accounting.exception.CompanyNotFoundException;
import com.cybertek.accounting.service.CompanyService;
import com.cybertek.accounting.service.TokenService;
import com.cybertek.accounting.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final UserService userService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());


        String username= httpServletRequest.getParameter("txtUsername");
        String password= httpServletRequest.getParameter("txtPassword");


        //after success login JWT token is creating and adding it into cookie
        LoginRequest loginRequest = new LoginRequest(username,password);

        JwtResponse tokenResponse = tokenService.authenticateUser(loginRequest);

        httpSession.setAttribute("Authorization", tokenResponse.getType()+" "+tokenResponse.getToken());

        //company session set

      UserDto user = userService.findByEmail(username);
        httpSession.setAttribute("company", user.getCompany());



        if(roles.contains("ROLE_ROOT")){
            httpServletResponse.sendRedirect("/company/list");
        }
        else {
            httpServletResponse.sendRedirect("/main");
        }

    }
}
