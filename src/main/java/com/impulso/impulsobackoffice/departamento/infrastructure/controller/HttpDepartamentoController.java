package com.impulso.impulsobackoffice.departamento.infrastructure.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.impulso.impulsobackoffice.departamento.application.dto.DepartamentoResponseDto;
import com.impulso.impulsobackoffice.departamento.application.dto.MunicipioResponseDto;
import com.impulso.impulsobackoffice.departamento.domain.port.in.RetrieveDepartamentoUseCasePort;
import com.impulso.impulsobackoffice.departamento.infrastructure.exception.DepartmentDataNotFoundException;
import com.impulso.impulsobackoffice.departamento.infrastructure.exception.DepartmentExternalServiceException;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/departamento")
public class HttpDepartamentoController {

    private static final Logger log = LoggerFactory.getLogger(HttpDepartamentoController.class);

    private final RetrieveDepartamentoUseCasePort retrieve;

    public HttpDepartamentoController(RetrieveDepartamentoUseCasePort retrieve) {
        this.retrieve = retrieve;
    }

    @GetMapping()
    public ResponseEntity<List<DepartamentoResponseDto>> getAllDepartamentos(HttpServletRequest request)
            throws DepartmentDataNotFoundException, DepartmentExternalServiceException {
        log.info("method={}, path={}", request.getMethod(), request.getRequestURI());

        List<DepartamentoResponseDto> departamentos = retrieve.getAllDepartamentos();
        log.info("created, departamentos={}", departamentos);

        return ResponseEntity.ok(departamentos);
    }

    @GetMapping("/{id}/municipios")
    public ResponseEntity<List<MunicipioResponseDto>> getMunicipios(HttpServletRequest request, @PathVariable int id) throws DepartmentExternalServiceException{
        log.info("method={}, path={}, id={}", request.getMethod(), request.getRequestURI(), id);

        if (id <= 0) {
            log.info("bad request, id={}", id);
            return ResponseEntity.badRequest().body(List.of());
        }

        List<MunicipioResponseDto> municipios = retrieve.getMunicipiosById(id);
        log.info("created, municipios={}", municipios);

        return ResponseEntity.ok(municipios);
    }

}
