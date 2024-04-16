package com.impulso.impulsobackoffice.departamento.application.exception;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.impulso.impulsobackoffice.departamento.infrastructure.exception.DepartmentDataNotFoundException;
import com.impulso.impulsobackoffice.departamento.infrastructure.exception.DepartmentExternalServiceException;

@ControllerAdvice
public class RestResponseDepartamentoExceptionHandler extends ResponseEntityExceptionHandler{
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(RestResponseDepartamentoExceptionHandler.class);
    
    private static final String LOG_MESSAGE_EXCEPTION = "message, exception={}";

    @ExceptionHandler(DepartmentDataNotFoundException.class)
    public ProblemDetail handleDepartmentDataNotFound(DepartmentDataNotFoundException ex){
        log.error(LOG_MESSAGE_EXCEPTION, ex.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(DepartmentExternalServiceException.class)
    public ProblemDetail handleDepartmentExternalServiceException(DepartmentExternalServiceException ex) {
        log.error(LOG_MESSAGE_EXCEPTION, ex.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

}
