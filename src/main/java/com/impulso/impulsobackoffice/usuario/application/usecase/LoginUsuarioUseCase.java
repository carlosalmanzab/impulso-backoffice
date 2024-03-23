package com.impulso.impulsobackoffice.usuario.application.usecase;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.impulso.impulsobackoffice.auth.domain.model.Token;
import com.impulso.impulsobackoffice.auth.domain.ports.in.CreateAuthenticationTokenUseCasePort;
import com.impulso.impulsobackoffice.usuario.application.dtos.LoginRequestDto;
import com.impulso.impulsobackoffice.usuario.application.exceptions.InvalidAuthenticationLoginException;
import com.impulso.impulsobackoffice.usuario.application.exceptions.InvalidAuthenticationPasswordException;
import com.impulso.impulsobackoffice.usuario.domain.model.Usuario;
import com.impulso.impulsobackoffice.usuario.domain.ports.in.LoginUsuarioUseCasePort;
import com.impulso.impulsobackoffice.usuario.domain.ports.out.UsuarioRepositoryPort;

public class LoginUsuarioUseCase implements LoginUsuarioUseCasePort {

    private final UsuarioRepositoryPort usuarioRepository;
    private final CreateAuthenticationTokenUseCasePort createAuthenticationToken;
    private final PasswordEncoder passwordEncoder;

    public LoginUsuarioUseCase(UsuarioRepositoryPort usuarioRepository, PasswordEncoder passwordEncoder,
            CreateAuthenticationTokenUseCasePort createAuthenticationToken) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.createAuthenticationToken = createAuthenticationToken;
    }

    /**
     * Authenticates a user by verifying the provided login credentials.
     *
     * @param loginRequest the login request containing the username and password
     * @return the authentication token for the logged-in user
     * @throws InvalidAuthenticationLoginException    if the login credentials are
     *                                                invalid
     * @throws InvalidAuthenticationPasswordException if the password is invalid
     */
    @Override
    public Token login(LoginRequestDto loginRequest)
            throws InvalidAuthenticationLoginException, InvalidAuthenticationPasswordException {

        final Optional<Usuario> usuario = usuarioRepository.findBycorreoElectronico(loginRequest.username());

        ensureUsuarioExist(usuario, loginRequest);
        ensureCredentialsAreValid(usuario.get(), loginRequest);

        return new Token(
                createAuthenticationToken.createToken(usuario.get().getCorreoElectronico()));
    }

    /**
     * Ensure that the usuario exists.
     *
     * @param usuario      the optional usuario
     * @param loginRequest the login request dto
     */
    private void ensureUsuarioExist(Optional<Usuario> usuario, LoginRequestDto loginRequest) {
        if (!usuario.isPresent()) {
            throw new InvalidAuthenticationLoginException(loginRequest.username());
        }
    }

    /**
     * Ensures that the provided credentials are valid for the given user.
     *
     * @param usuario      the user for whom the credentials are being checked
     * @param loginRequest the login request containing the credentials to be
     *                     checked
     * @throws InvalidAuthenticationPasswordException if the provided password does
     *                                                not match the user's password
     */
    private void ensureCredentialsAreValid(Usuario usuario, LoginRequestDto loginRequest) {
        if (!passwordEncoder.matches(loginRequest.password(), usuario.getPassword())) {
            throw new InvalidAuthenticationPasswordException(loginRequest.username());
        }
    }

}
