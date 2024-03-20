package com.impulso.impulsobackoffice.usuario.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.impulso.impulsobackoffice.usuario.application.usecase.CreateUsuarioUseCase;
import com.impulso.impulsobackoffice.usuario.application.usecase.DeleteUsuarioUseCase;
import com.impulso.impulsobackoffice.usuario.application.usecase.RetrieveUsuarioUseCase;
import com.impulso.impulsobackoffice.usuario.application.usecase.UpdateUsuarioUseCase;
import com.impulso.impulsobackoffice.usuario.domain.ports.in.CreateUsuarioUseCasePort;
import com.impulso.impulsobackoffice.usuario.domain.ports.in.DeleteUsuarioUseCasePort;
import com.impulso.impulsobackoffice.usuario.domain.ports.in.RetrieveUsuarioUseCasePort;
import com.impulso.impulsobackoffice.usuario.domain.ports.in.UpdateUsuarioUseCasePort;
import com.impulso.impulsobackoffice.usuario.domain.ports.out.UsuarioRepositoryPort;
import com.impulso.impulsobackoffice.usuario.infrastructure.repositories.JpaUsuarioRepositoryAdapter;

@Configuration
public class ApplicationConfig {

    /**
     * Creates a new instance of the CreateUsuarioUseCasePort bean.
     *
     * @param usuarioRepositoryPort the UsuarioRepositoryPort used to create the use
     *                              case
     * @return the newly created CreateUsuarioUseCasePort instance
     */
    @Bean
    public CreateUsuarioUseCasePort createUsuarioUseCasePort(UsuarioRepositoryPort usuarioRepositoryPort) {
        return new CreateUsuarioUseCase(usuarioRepositoryPort);
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
