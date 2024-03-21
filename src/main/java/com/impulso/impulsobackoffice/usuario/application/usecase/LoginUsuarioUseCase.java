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

    @Override
    public Token login(LoginRequestDto loginRequest) {

        final Optional<Usuario> usuario = usuarioRepository.findBycorreoElectronico(loginRequest.username());

        ensureUsuarioExist(usuario, loginRequest);
        ensureCredentialsAreValid(usuario.get(), loginRequest);

        return new Token(
                createAuthenticationToken.createToken(usuario.get().getCorreoElectronico()));
    }

    private void ensureUsuarioExist(Optional<Usuario> usuario, LoginRequestDto loginRequest) {
        if (!usuario.isPresent()) {
            throw new InvalidAuthenticationLoginException(loginRequest.username());
        }
    }

    private void ensureCredentialsAreValid(Usuario usuario, LoginRequestDto loginRequest) {
        if (!passwordEncoder.matches(loginRequest.password(), usuario.getPassword())) {
            throw new InvalidAuthenticationPasswordException(loginRequest.username());
        }
    }

}
