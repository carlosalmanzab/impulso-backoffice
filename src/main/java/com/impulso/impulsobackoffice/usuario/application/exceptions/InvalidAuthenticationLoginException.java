package com.impulso.impulsobackoffice.usuario.application.exceptions;

public class InvalidAuthenticationLoginException extends RuntimeException {

    public InvalidAuthenticationLoginException(final String correoElectronico) {
        super(String.format("El usuario <%s> no existe",correoElectronico) );
    }

}
