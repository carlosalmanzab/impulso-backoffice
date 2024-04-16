package com.impulso.impulsobackoffice.departamento.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.impulso.impulsobackoffice.departamento.application.usecase.RetrieveDepartamentoUseCase;
import com.impulso.impulsobackoffice.departamento.domain.port.in.RetrieveDepartamentoUseCasePort;
import com.impulso.impulsobackoffice.departamento.domain.port.out.DepartamentoExternalServicePort;
import com.impulso.impulsobackoffice.departamento.infrastructure.adapter.DepartamentoExternalServiceAdapter;

@Configuration
public class DepartamentoConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RetrieveDepartamentoUseCasePort retrieveDepartamentoUseCasePort(DepartamentoExternalServicePort departmentExternalServicePort) {
        return new RetrieveDepartamentoUseCase(departmentExternalServicePort);
    }

    @Bean
    public DepartamentoExternalServicePort departamentoExternalServicePort(RestTemplate restTemplate) {
        return new DepartamentoExternalServiceAdapter(restTemplate);
    }
}
