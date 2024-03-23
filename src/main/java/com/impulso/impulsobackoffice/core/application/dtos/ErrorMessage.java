package com.impulso.impulsobackoffice.core.application.dtos;

import java.util.Map;

import org.springframework.http.HttpStatus;

public record ErrorMessage(
        HttpStatus status,
        String message,
        Map<String, String> errors) {
    public ErrorMessage(HttpStatus status, String message) {
        this(status, message, null);
    }
}
