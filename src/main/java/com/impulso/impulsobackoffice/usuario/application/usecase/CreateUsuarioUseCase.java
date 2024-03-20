package com.impulso.impulsobackoffice.usuario.application.usecase;

import com.impulso.impulsobackoffice.usuario.application.exceptions.UsuarioAlreadyExistsException;
import com.impulso.impulsobackoffice.usuario.domain.model.Usuario;
import com.impulso.impulsobackoffice.usuario.domain.ports.in.CreateUsuarioUseCasePort;
import com.impulso.impulsobackoffice.usuario.domain.ports.out.UsuarioRepositoryPort;

public class CreateUsuarioUseCase implements CreateUsuarioUseCasePort {

    private final UsuarioRepositoryPort usuarioRepository;

    public CreateUsuarioUseCase(UsuarioRepositoryPort usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario createUsuario(Usuario usuario) throws UsuarioAlreadyExistsException {

        if (this.usuarioRepository.existsByIdentificacion(usuario.getIdentificacion())) {
            throw new UsuarioAlreadyExistsException(usuario.getIdentificacion());
        }

        return this.usuarioRepository.save(usuario);
    }

}
