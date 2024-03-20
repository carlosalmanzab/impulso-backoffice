package com.impulso.impulsobackoffice.auth.domain.ports.in;

public interface CreateAuthenticationTokenUseCasePort {
    String createToken(final String username);
}
