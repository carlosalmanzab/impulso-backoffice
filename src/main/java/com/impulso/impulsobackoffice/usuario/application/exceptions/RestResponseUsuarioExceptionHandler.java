package com.impulso.impulsobackoffice.usuario.application.exceptions;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.impulso.impulsobackoffice.core.application.dtos.ErrorMessage;

@ControllerAdvice
public class RestResponseUsuarioExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(RestResponseUsuarioExceptionHandler.class);

    private static final String LOG_MESSAGE_EXCEPTION = "message, exception={}";

    @ExceptionHandler(UsuarioAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> handleUserAlreadyExists(UsuarioAlreadyExistsException ex) {
        ErrorMessage errorResponse = new ErrorMessage(HttpStatus.CONFLICT, ex.getMessage());
        log.error(LOG_MESSAGE_EXCEPTION, ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(InvalidAuthenticationLoginException.class)
    public ResponseEntity<ErrorMessage> handleInvalidAuthenticationLogin(InvalidAuthenticationLoginException ex) {
        ErrorMessage errorResponse = new ErrorMessage(HttpStatus.UNAUTHORIZED, ex.getMessage());
        log.error(LOG_MESSAGE_EXCEPTION, ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(InvalidAuthenticationPasswordException.class)
    public ResponseEntity<ErrorMessage> handleInvalidAuthenticationPassword(InvalidAuthenticationPasswordException ex) {
        ErrorMessage errorResponse = new ErrorMessage(HttpStatus.UNAUTHORIZED, ex.getMessage());
        log.error(LOG_MESSAGE_EXCEPTION, ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
}
