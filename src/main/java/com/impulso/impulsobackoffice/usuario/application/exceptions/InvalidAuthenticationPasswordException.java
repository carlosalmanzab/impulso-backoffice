package com.impulso.impulsobackoffice.usuario.application.exceptions;

public class InvalidAuthenticationPasswordException extends RuntimeException {
    public InvalidAuthenticationPasswordException(String correoElectronico) {
        super(String.format("Las credenciales para <%s> no son válidas", correoElectronico));
    }
}
