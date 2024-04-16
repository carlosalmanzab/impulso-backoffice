package com.impulso.impulsobackoffice.departamento.application.usecase;

import java.util.List;

import com.impulso.impulsobackoffice.departamento.application.dto.DepartamentoResponseDto;
import com.impulso.impulsobackoffice.departamento.application.dto.MunicipioResponseDto;
import com.impulso.impulsobackoffice.departamento.domain.port.in.RetrieveDepartamentoUseCasePort;
import com.impulso.impulsobackoffice.departamento.domain.port.out.DepartamentoExternalServicePort;
import com.impulso.impulsobackoffice.departamento.infrastructure.exception.DepartmentDataNotFoundException;
import com.impulso.impulsobackoffice.departamento.infrastructure.exception.DepartmentExternalServiceException;

public class RetrieveDepartamentoUseCase implements RetrieveDepartamentoUseCasePort {

    private final DepartamentoExternalServicePort departmentExternalServicePort;

    public RetrieveDepartamentoUseCase(DepartamentoExternalServicePort departmentExternalServicePort) {
        this.departmentExternalServicePort = departmentExternalServicePort;
    }

    /**
     * A description of the entire Java function.
     *
     * @param paramName description of parameter
     * @return description of return value
     */
    @Override
    public List<DepartamentoResponseDto> getAllDepartamentos()
            throws DepartmentDataNotFoundException, DepartmentExternalServiceException {
                return this.departmentExternalServicePort
                .getDepartamentos().stream().map(DepartamentoResponseDto::fromDepartamento).toList();
    }

    /**
     * Retrieves a list of Municipio objects by their ID.
     *
     * @param id the ID of the Municipio objects to retrieve
     * @return a list of Municipio objects
     */
    @Override
    public List<MunicipioResponseDto> getMunicipiosById(int id) throws DepartmentExternalServiceException {
        return this.departmentExternalServicePort.getMunicipiosByDepartamentoId(id)
        .stream().map(MunicipioResponseDto::fromMunicipios).toList();
    }

}
