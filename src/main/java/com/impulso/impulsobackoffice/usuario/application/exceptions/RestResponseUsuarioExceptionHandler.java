package com.impulso.impulsobackoffice.usuario.application.exceptions;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseUsuarioExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(RestResponseUsuarioExceptionHandler.class);

    private static final String LOG_MESSAGE_EXCEPTION = "message, exception={}";

    @ExceptionHandler(UsuarioAlreadyExistsException.class)
    public ProblemDetail handleUserAlreadyExists(UsuarioAlreadyExistsException ex) {
        log.error(LOG_MESSAGE_EXCEPTION, ex.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(InvalidAuthenticationLoginException.class)
    public ProblemDetail handleInvalidAuthenticationLogin(InvalidAuthenticationLoginException ex) {
        log.error(LOG_MESSAGE_EXCEPTION, ex.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(InvalidAuthenticationPasswordException.class)
    public ProblemDetail handleInvalidAuthenticationPassword(InvalidAuthenticationPasswordException ex) {
        log.error(LOG_MESSAGE_EXCEPTION, ex.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }
}
