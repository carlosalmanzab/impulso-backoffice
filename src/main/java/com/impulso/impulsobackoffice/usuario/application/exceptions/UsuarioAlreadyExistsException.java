package com.impulso.impulsobackoffice.usuario.application.exceptions;

import java.text.MessageFormat;

public class UsuarioAlreadyExistsException extends Exception {
    private static final long serialVersionUID = 1L;

    public UsuarioAlreadyExistsException(int identificacion) {
        super(MessageFormat.format("El usuario con identificación {0} ya existe", identificacion));
    }
}
