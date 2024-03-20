package com.impulso.impulsobackoffice.auth.application.usecase;

import java.util.Optional;

import com.impulso.impulsobackoffice.auth.domain.model.Token;
import com.impulso.impulsobackoffice.auth.domain.ports.in.ValidateAuthenticationTokenUseCasePort;
import com.impulso.impulsobackoffice.auth.domain.ports.out.AuthenticationTokenProviderPort;
import com.impulso.impulsobackoffice.usuario.domain.model.Usuario;
import com.impulso.impulsobackoffice.usuario.domain.ports.out.UsuarioRepositoryPort;

public class ValidateAuthenticationTokenUseCase implements ValidateAuthenticationTokenUseCasePort {
    private final AuthenticationTokenProviderPort tokenProvider;
    private final UsuarioRepositoryPort usuarioRepository;

    public ValidateAuthenticationTokenUseCase(AuthenticationTokenProviderPort tokenProvider, UsuarioRepositoryPort usuarioRepository) {
        this.tokenProvider = tokenProvider;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Validates the given token and returns the associated Usuario.
     *
     * @param token the token to be validated
     * @return the associated Usuario object
     */
    @Override
    public Usuario validToken(Token token) {
        final String correoElectronico = tokenProvider.validToken(token);
        final Optional<Usuario> usuario = usuarioRepository.findBycorreoElectronico(correoElectronico);
        return usuario.get();
    }

}
