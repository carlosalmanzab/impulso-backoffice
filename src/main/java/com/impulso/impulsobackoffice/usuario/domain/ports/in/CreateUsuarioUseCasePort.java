package com.impulso.impulsobackoffice.usuario.domain.ports.in;

import com.impulso.impulsobackoffice.usuario.application.exceptions.UsuarioAlreadyExistsException;
import com.impulso.impulsobackoffice.usuario.domain.model.Usuario;

public interface CreateUsuarioUseCasePort {
    Usuario createUsuario(Usuario usuario) throws UsuarioAlreadyExistsException;
}
