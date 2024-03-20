package com.impulso.impulsobackoffice.auth.domain.ports.out;

import com.impulso.impulsobackoffice.auth.domain.model.Token;

public interface AuthenticationTokenProviderPort {
    /**
     * Create a token from the username and return a new token.
     * @param username
     * @return token
     * */
    Token createToken(final String username);
    /**
     * Validate the token and return the email.
     * @param token
     * @return email
     */
    String validToken(final Token token);
}