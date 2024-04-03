package com.impulso.impulsobackoffice.usuario.application.exceptions;

import java.text.MessageFormat;

public class UsuarioAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UsuarioAlreadyExistsException(int identificacion) {
        super(MessageFormat.format("El usuario con identificación {0} ya existe", identificacion));
    }

    public UsuarioAlreadyExistsException(String correoElectronico) {
        super(MessageFormat.format("El usuario con correo electronico {0} ya existe", correoElectronico));
    }
}
