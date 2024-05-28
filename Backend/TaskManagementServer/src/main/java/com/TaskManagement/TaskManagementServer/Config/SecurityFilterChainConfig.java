package com.TaskManagement.TaskManagementServer.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityFilterChainConfig {

    @Autowired
    AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    JWTAuthenticationFilter jwtAuthenticationFilter;//userdefined
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        // disable cors
        httpSecurity.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.disable());
        // disable csrf
        httpSecurity.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());
        //http request filter
        httpSecurity.authorizeHttpRequests(request->
            request.requestMatchers("/auth/**")
                    .permitAll()
                    .requestMatchers("/swagger-ui.html/**","swagger-ui/**", "swagger-ui**", "/v3/api-docs/**", "/v3/api-docs**")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
        );
        // Authentication Entry Point -> Exception Handler
        httpSecurity.exceptionHandling(
                exceptionConfig->exceptionConfig.authenticationEntryPoint(authenticationEntryPoint)
        );
        //add jwt authentication filter
        httpSecurity.addFilterBefore(jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
