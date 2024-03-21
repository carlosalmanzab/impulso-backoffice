package com.impulso.impulsobackoffice.usuario.application.usecase;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.impulso.impulsobackoffice.auth.domain.model.Token;
import com.impulso.impulsobackoffice.auth.domain.ports.in.CreateAuthenticationTokenUseCasePort;
import com.impulso.impulsobackoffice.usuario.application.dtos.RegisterRequestDto;
import com.impulso.impulsobackoffice.usuario.application.exceptions.UsuarioAlreadyExistsException;
import com.impulso.impulsobackoffice.usuario.domain.model.Usuario;
import com.impulso.impulsobackoffice.usuario.domain.ports.in.RegisterUsuarioUseCasePort;
import com.impulso.impulsobackoffice.usuario.domain.ports.out.UsuarioRepositoryPort;

public class RegisterUsuarioUseCase implements RegisterUsuarioUseCasePort {

    private final CreateAuthenticationTokenUseCasePort createAuthenticationToken;
    private final UsuarioRepositoryPort usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterUsuarioUseCase(CreateAuthenticationTokenUseCasePort createAuthenticationToken,
            UsuarioRepositoryPort usuarioRepository, PasswordEncoder passwordEncoder) {
        this.createAuthenticationToken = createAuthenticationToken;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user with the provided registration request data.
     *
     * @param registerRequest the registration request data containing user
     *                        information
     * @return the authentication token for the newly registered user
     */
    @Override
    public Token register(RegisterRequestDto registerRequest) {
        // check if user exists
        final Optional<Usuario> usuario = usuarioRepository
                .findBycorreoElectronico(registerRequest.correoElectronico());

        ensureUserDoesNotExist(registerRequest, usuario);

        // save user
        final Usuario user = RegisterRequestDto
        .toUsuario(registerRequest, passwordEncoder.encode(registerRequest.password()));

        usuarioRepository.save(user);

        // return token
        return new Token(
                createAuthenticationToken.createToken(user.getCorreoElectronico()));
    }

    /**
     * Ensure that the user does not already exist and throw an exception if they
     * do.
     *
     * @param registerRequest the registration request DTO
     * @param usuario         an optional containing the existing user if present
     */
    private void ensureUserDoesNotExist(RegisterRequestDto registerRequest, final Optional<Usuario> usuario) {
        if (usuario.isPresent()) {
            try {
                throw new UsuarioAlreadyExistsException(registerRequest.correoElectronico());
            } catch (UsuarioAlreadyExistsException e) {
                e.printStackTrace();
            }
        }
    }

}
