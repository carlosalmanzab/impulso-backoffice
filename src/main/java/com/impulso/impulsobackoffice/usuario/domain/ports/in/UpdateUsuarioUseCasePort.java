package com.impulso.impulsobackoffice.usuario.domain.ports.in;

import java.util.Optional;
import java.util.UUID;

import com.impulso.impulsobackoffice.usuario.domain.model.Usuario;

public interface UpdateUsuarioUseCasePort {
    Optional<Usuario> updateUsuario(UUID id, Usuario updateUsuario);
}
