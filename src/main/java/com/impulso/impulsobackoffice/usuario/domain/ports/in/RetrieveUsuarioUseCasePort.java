package com.impulso.impulsobackoffice.usuario.domain.ports.in;

import java.util.List;
import java.util.UUID;

import com.impulso.impulsobackoffice.usuario.application.exceptions.UsuarioNotFoundException;
import com.impulso.impulsobackoffice.usuario.domain.model.Usuario;

public interface RetrieveUsuarioUseCasePort {

    Usuario getUsuarioById(UUID id) throws UsuarioNotFoundException;
    List<Usuario> getAllUsuarios();
}
