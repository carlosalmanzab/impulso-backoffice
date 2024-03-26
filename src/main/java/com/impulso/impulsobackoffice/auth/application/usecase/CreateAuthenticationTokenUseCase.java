package com.impulso.impulsobackoffice.auth.application.usecase;

import com.impulso.impulsobackoffice.auth.domain.ports.in.CreateAuthenticationTokenUseCasePort;
import com.impulso.impulsobackoffice.auth.domain.ports.out.AuthenticationTokenProviderPort;

public class CreateAuthenticationTokenUseCase implements CreateAuthenticationTokenUseCasePort {

    private final AuthenticationTokenProviderPort tokenProvider;

    public CreateAuthenticationTokenUseCase(AuthenticationTokenProviderPort tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    /**
     * Generate a token for the given username.
     *
     * @param username the username for which the token is created
     * @return a string representation of the created token
     */
    @Override
    public String createToken(String username) {
        return tokenProvider.createToken(username);
    }

}
