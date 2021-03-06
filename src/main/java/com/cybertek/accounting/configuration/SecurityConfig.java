package com.cybertek.accounting.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private final UserPrincipalDetailsService securityService;
    private final AuthSuccessHandler authSuccessHandler;

    public SecurityConfig(UserPrincipalDetailsService securityService, AuthSuccessHandler authSuccessHandler) {
        this.securityService = securityService;
        this.authSuccessHandler = authSuccessHandler;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
 /*
        http
                .authorizeRequests()
                .antMatchers("/company/**").hasRole("ROOT")
                .antMatchers("/user/**").hasAnyRole("ADMIN","ROOT")
                .antMatchers("/invoice/**").hasAnyRole("MANAGER","EMPLOYEE")
                .antMatchers(
                        "/",
                        "/login",
                        "/fragments/**",
                        "/assets/**",
                        "/images/**"
                ).permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(authSuccessHandler)
                .failureUrl("/login?error=true")
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .and()
                .rememberMe()
                //.tokenValiditySeconds(120)
                .key("cybertekSecret")
                .userDetailsService(securityService);

        */
        http.authorizeRequests()
                .antMatchers("/company/**").hasRole("ROOT")
                .antMatchers("/user/**").hasAnyRole("ADMIN","ROOT")
                .antMatchers("/invoice/**").hasAnyRole("MANAGER","EMPLOYEE")
                .and()
                .formLogin()
                .loginProcessingUrl("/signin")
                .loginPage("/login")
                .permitAll()
                .usernameParameter("txtUsername")
                .passwordParameter("txtPassword")
                .successHandler(authSuccessHandler)
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .and()
                .rememberMe()
                .tokenValiditySeconds(3600)
                .key("mySecret!")
                .rememberMeParameter("checkRememberMe")
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied");


    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.securityService);

        return daoAuthenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

}
