package com.impulso.impulsobackoffice.core.application.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @SuppressWarnings("null") MethodArgumentNotValidException ex,
            @SuppressWarnings("null") HttpHeaders headers,
            @SuppressWarnings("null") HttpStatusCode status,
            @SuppressWarnings("null") WebRequest request) {

        Map<String, Object> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problem.setTitle(ex.getMessage());
        problem.setProperty("InvalidParams", errors);

        log.error("message, exception={}, errors={}", ex.getMessage(), errors);

        return ResponseEntity.status(status).body(problem);
    }

}
