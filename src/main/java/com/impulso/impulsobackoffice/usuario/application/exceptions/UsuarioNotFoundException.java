package com.impulso.impulsobackoffice.usuario.application.exceptions;

public class UsuarioNotFoundException extends RuntimeException {

    public UsuarioNotFoundException() {
        super("El usuario no existe");
    }
}
