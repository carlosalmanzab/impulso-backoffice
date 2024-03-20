package com.impulso.impulsobackoffice.usuario.application.exceptions;

public class UsuarioNotFoundException extends Exception {

    public UsuarioNotFoundException() {
        super("El usuario no existe");
    }
}
