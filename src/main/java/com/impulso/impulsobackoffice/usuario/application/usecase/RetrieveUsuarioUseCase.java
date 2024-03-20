package com.impulso.impulsobackoffice.usuario.application.usecase;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.impulso.impulsobackoffice.usuario.application.exceptions.UsuarioNotFoundException;
import com.impulso.impulsobackoffice.usuario.domain.model.Usuario;
import com.impulso.impulsobackoffice.usuario.domain.ports.in.RetrieveUsuarioUseCasePort;
import com.impulso.impulsobackoffice.usuario.domain.ports.out.UsuarioRepositoryPort;

public class RetrieveUsuarioUseCase implements RetrieveUsuarioUseCasePort {

    private final UsuarioRepositoryPort usuarioRepository;

    public RetrieveUsuarioUseCase(UsuarioRepositoryPort usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario getUsuarioById(UUID id) throws UsuarioNotFoundException{
        Optional<Usuario> usuario = this.usuarioRepository.findById(id);
        if (!usuario.isPresent()) {
            throw new UsuarioNotFoundException();
        }
        return usuario.get();
    }
    @Override
    public List<Usuario> getAllUsuarios() {
        return (List<Usuario>) this.usuarioRepository.findAll();
    }

}
