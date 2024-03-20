package com.impulso.impulsobackoffice.usuario.application.usecase;

import java.util.Optional;
import java.util.UUID;

import com.impulso.impulsobackoffice.usuario.domain.model.Usuario;
import com.impulso.impulsobackoffice.usuario.domain.ports.in.UpdateUsuarioUseCasePort;
import com.impulso.impulsobackoffice.usuario.domain.ports.out.UsuarioRepositoryPort;

public class UpdateUsuarioUseCase implements UpdateUsuarioUseCasePort {

    private final UsuarioRepositoryPort usuarioRepository;

    public UpdateUsuarioUseCase(UsuarioRepositoryPort usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Optional<Usuario> updateUsuario(UUID id, Usuario updateUsuario) {
        return usuarioRepository.update(updateUsuario);
    }

}
