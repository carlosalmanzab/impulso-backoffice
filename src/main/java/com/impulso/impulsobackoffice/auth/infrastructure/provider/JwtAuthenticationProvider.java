package com.impulso.impulsobackoffice.auth.infrastructure.provider;

import java.util.Base64;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.impulso.impulsobackoffice.auth.domain.model.Token;
import com.impulso.impulsobackoffice.auth.domain.ports.out.AuthenticationTokenProviderPort;

public class JwtAuthenticationProvider implements AuthenticationTokenProviderPort {

    private final String secretKey;

    public JwtAuthenticationProvider(final String secretKey) {
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    @Override
    public String createToken(String username) {
        final Date now = new Date();
        final Date expirationDate = new Date(now.getTime() + 60 * 24 * 60 * 60 * 1000);// 30 days
        final Algorithm algorithm = Algorithm.HMAC256(secretKey);
        new JWT();
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(now)
                .withExpiresAt(expirationDate)
                .sign(algorithm);
    }

    @Override
    public String validToken(Token token) {
        final Algorithm algorithm = Algorithm.HMAC256(secretKey);
        final JWTVerifier verifier = JWT.require(algorithm).build();
        final DecodedJWT decoded = verifier.verify(token.getToken());
        return decoded.getSubject();
    }

}
