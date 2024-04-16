package com.impulso.impulsobackoffice.departamento.infrastructure.exception;

public class DepartmentDataNotFoundException extends RuntimeException {

    public DepartmentDataNotFoundException() {
        super("No se encontraron datos de los departamentos en la respuesta");
    }
}
