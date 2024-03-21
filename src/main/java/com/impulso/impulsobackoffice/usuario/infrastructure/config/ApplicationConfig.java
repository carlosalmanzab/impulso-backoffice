package com.impulso.impulsobackoffice.usuario.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.impulso.impulsobackoffice.usuario.application.usecase.RegisterUsuarioUseCase;
import com.impulso.impulsobackoffice.auth.domain.ports.in.CreateAuthenticationTokenUseCasePort;
import com.impulso.impulsobackoffice.usuario.application.usecase.DeleteUsuarioUseCase;
import com.impulso.impulsobackoffice.usuario.application.usecase.LoginUsuarioUseCase;
import com.impulso.impulsobackoffice.usuario.application.usecase.RetrieveUsuarioUseCase;
import com.impulso.impulsobackoffice.usuario.application.usecase.UpdateUsuarioUseCase;
import com.impulso.impulsobackoffice.usuario.domain.ports.in.DeleteUsuarioUseCasePort;
import com.impulso.impulsobackoffice.usuario.domain.ports.in.LoginUsuarioUseCasePort;
import com.impulso.impulsobackoffice.usuario.domain.ports.in.RegisterUsuarioUseCasePort;
import com.impulso.impulsobackoffice.usuario.domain.ports.in.RetrieveUsuarioUseCasePort;
import com.impulso.impulsobackoffice.usuario.domain.ports.in.UpdateUsuarioUseCasePort;
import com.impulso.impulsobackoffice.usuario.domain.ports.out.UsuarioRepositoryPort;
import com.impulso.impulsobackoffice.usuario.infrastructure.repositories.JpaUsuarioRepositoryAdapter;

@Configuration
public class ApplicationConfig {

    /**
     * Creates a RegisterUsuarioUseCasePort bean.
     *
     * @param createAuthenticationTokenUseCasePort the
     *                                             CreateAuthenticationTokenUseCasePort
     *                                             object
     * @param usuarioRepositoryPort                the UsuarioRepositoryPort object
     * @param passwordEncoder                      the PasswordEncoder object
     * @return the RegisterUsuarioUseCasePort object
     */
    @Bean
    public RegisterUsuarioUseCasePort registerUsuarioUseCasePort(
            CreateAuthenticationTokenUseCasePort createAuthenticationTokenUseCasePort,
            UsuarioRepositoryPort usuarioRepositoryPort,
            PasswordEncoder passwordEncoder) {
        return new RegisterUsuarioUseCase(
                createAuthenticationTokenUseCasePort,
                usuarioRepositoryPort,
                passwordEncoder);
    }

    /**
     * Creates a new instance of the LoginUsuarioUseCasePort class.
     *
     * @param usuarioRepositoryPort                the repository port for managing
     *                                             usuarios
     * @param createAuthenticationTokenUseCasePort the use case port for creating
     *                                             authentication tokens
     * @param passwordEncoder                      the password encoder for
     *                                             encrypting passwords
     * @return the newly created LoginUsuarioUseCasePort instance
     */
    @Bean
    public LoginUsuarioUseCasePort loginUsuarioUseCasePort(UsuarioRepositoryPort usuarioRepositoryPort,
            CreateAuthenticationTokenUseCasePort createAuthenticationTokenUseCasePort,
            PasswordEncoder passwordEncoder) {
        return new LoginUsuarioUseCase(
                usuarioRepositoryPort,
                passwordEncoder,
                createAuthenticationTokenUseCasePort);
    }

    /**
     * Creates and returns an instance of the UpdateUsuarioUseCasePort class.
     *
     * @param usuarioRepositoryPort the UsuarioRepositoryPort used by the
     *                              UpdateUsuarioUseCase
     * @return an instance of the UpdateUsuarioUseCasePort class
     */
    @Bean
    public UpdateUsuarioUseCasePort updateUsuarioUseCasePort(UsuarioRepositoryPort usuarioRepositoryPort) {
        return new UpdateUsuarioUseCase(usuarioRepositoryPort);
    }

    /**
     * Creates a new instance of DeleteUsuarioUseCasePort using the provided
     * UsuarioRepositoryPort.
     *
     * @param usuarioRepositoryPort the repository port for managing usuarios
     * @return the newly created DeleteUsuarioUseCasePort instance
     */
    @Bean
    public DeleteUsuarioUseCasePort deleteUsuarioUseCasePort(UsuarioRepositoryPort usuarioRepositoryPort) {
        return new DeleteUsuarioUseCase(usuarioRepositoryPort);
    }

    /**
     * Creates a new instance of RetrieveUsuarioUseCasePort using the provided
     * UsuarioRepositoryPort.
     *
     * @param usuarioRepositoryPort the repository port for retrieving usuario
     *                              information
     * @return the newly created RetrieveUsuarioUseCasePort instance
     */
    @Bean
    public RetrieveUsuarioUseCasePort retrieveUsuarioUseCasePort(UsuarioRepositoryPort usuarioRepositoryPort) {
        return new RetrieveUsuarioUseCase(usuarioRepositoryPort);
    }

    /**
     * A description of the entire Java function.
     *
     * @param jpaUsuarioRepositoryAdapter description of parameter
     * @return description of return value
     */
    @Bean
    public UsuarioRepositoryPort usuarioRepositoryPort(JpaUsuarioRepositoryAdapter jpaUsuarioRepositoryAdapter) {
        return jpaUsuarioRepositoryAdapter;
    }

}
