package com.impulso.impulsobackoffice.usuario.infrastructure.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.impulso.impulsobackoffice.auth.domain.model.Token;
import com.impulso.impulsobackoffice.usuario.application.dtos.LoginRequestDto;
import com.impulso.impulsobackoffice.usuario.application.dtos.RegisterRequestDto;
import com.impulso.impulsobackoffice.usuario.application.exceptions.InvalidAuthenticationLoginException;
import com.impulso.impulsobackoffice.usuario.application.exceptions.InvalidAuthenticationPasswordException;
import com.impulso.impulsobackoffice.usuario.application.exceptions.UsuarioAlreadyExistsException;
import com.impulso.impulsobackoffice.usuario.domain.ports.in.LoginUsuarioUseCasePort;
import com.impulso.impulsobackoffice.usuario.domain.ports.in.RegisterUsuarioUseCasePort;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "api/auth", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class HttpAuthController {
    private static final Logger log = LoggerFactory.getLogger(HttpAuthController.class);
    private final RegisterUsuarioUseCasePort register;
    private final LoginUsuarioUseCasePort login;

    public HttpAuthController(RegisterUsuarioUseCasePort register, LoginUsuarioUseCasePort login) {
        this.register = register;
        this.login = login;
    }

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Token> register(
            HttpServletRequest request, @RequestBody RegisterRequestDto body)
            throws UsuarioAlreadyExistsException {
        log.info("method={}, path={}, body={}", request.getMethod(), request.getRequestURI(), body);

        Token token = register.register(body);
        log.info("created, token={}", token);

        return ResponseEntity.ok(token);
    }

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Token> login(HttpServletRequest request, @RequestBody LoginRequestDto body)
            throws InvalidAuthenticationLoginException, InvalidAuthenticationPasswordException {
        log.info("method={}, path={}, body={}", request.getMethod(), request.getRequestURI(), body);

        Token token = login.login(body);
        log.info("created, token={}", token);

        return ResponseEntity.ok(token);
    }

}
