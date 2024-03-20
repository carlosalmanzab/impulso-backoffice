package com.impulso.impulsobackoffice.auth.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.impulso.impulsobackoffice.auth.application.usecase.CreateAuthenticationTokenUseCase;
import com.impulso.impulsobackoffice.auth.application.usecase.ValidateAuthenticationTokenUseCase;
import com.impulso.impulsobackoffice.auth.domain.ports.in.CreateAuthenticationTokenUseCasePort;
import com.impulso.impulsobackoffice.auth.domain.ports.in.ValidateAuthenticationTokenUseCasePort;
import com.impulso.impulsobackoffice.auth.domain.ports.out.AuthenticationTokenProviderPort;
import com.impulso.impulsobackoffice.auth.infrastructure.provider.JwtAuthenticationProvider;
import com.impulso.impulsobackoffice.usuario.domain.ports.out.UsuarioRepositoryPort;

@Configuration
public class ApplicationConfig {

    @Value("${jwt.secret}")
    private String secretKey;

    @Bean
    public ValidateAuthenticationTokenUseCasePort validateAuthenticationTokenUseCasePort(
            AuthenticationTokenProviderPort authenticationTokenProviderPort,
            UsuarioRepositoryPort usuarioRepositoryPort) {
        return new ValidateAuthenticationTokenUseCase(authenticationTokenProviderPort, usuarioRepositoryPort);
    }

    @Bean
    public CreateAuthenticationTokenUseCasePort createAuthenticationTokenUseCasePort(
            AuthenticationTokenProviderPort authenticationTokenProviderPort) {
        return new CreateAuthenticationTokenUseCase(authenticationTokenProviderPort);
    }

    @Bean
    public AuthenticationTokenProviderPort authenticationTokenProviderPort() {
        return new JwtAuthenticationProvider(this.secretKey);
    }
}
