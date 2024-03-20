package com.impulso.impulsobackoffice.usuario.application.usecase;

import java.util.UUID;

import com.impulso.impulsobackoffice.usuario.domain.ports.in.DeleteUsuarioUseCasePort;
import com.impulso.impulsobackoffice.usuario.domain.ports.out.UsuarioRepositoryPort;

public class DeleteUsuarioUseCase implements DeleteUsuarioUseCasePort {

    private final UsuarioRepositoryPort usuarioRepository;

    public DeleteUsuarioUseCase(UsuarioRepositoryPort usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public boolean deleteUsuario(UUID id) {
        return this.usuarioRepository.deleteById(id);
    }

}
