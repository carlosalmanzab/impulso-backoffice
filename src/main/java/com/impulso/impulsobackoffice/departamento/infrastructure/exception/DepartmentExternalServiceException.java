package com.impulso.impulsobackoffice.departamento.infrastructure.exception;

public class DepartmentExternalServiceException extends RuntimeException {

    public DepartmentExternalServiceException(Exception e) {
        super("Error al recuperar los departamentos",e);
    }
}
