package com.impulso.impulsobackoffice.usuario.domain.ports.in;

import java.util.UUID;

public interface DeleteUsuarioUseCasePort {
    boolean deleteUsuario(UUID id);
}
