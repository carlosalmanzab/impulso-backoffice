package com.impulso.impulsobackoffice.auth.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.impulso.impulsobackoffice.auth.domain.ports.in.ValidateAuthenticationTokenUseCasePort;
import com.impulso.impulsobackoffice.auth.infrastructure.middleware.BearerJwtAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final ValidateAuthenticationTokenUseCasePort validateAuthenticationToken;

    public SecurityConfig(ValidateAuthenticationTokenUseCasePort validateAuthenticationToken) {
        this.validateAuthenticationToken = validateAuthenticationToken;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean()
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.
        addFilterBefore(
            new BearerJwtAuthenticationProvider(validateAuthenticationToken)
            , BasicAuthenticationFilter.class
            )
            .csrf(csrfConf -> csrfConf.disable())
            .sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
            .authorizeHttpRequests(
                requests -> requests.requestMatchers(HttpMethod.POST, "api/auth/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                )
                .build();
    }
}
