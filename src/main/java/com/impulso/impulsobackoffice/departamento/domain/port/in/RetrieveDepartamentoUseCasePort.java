package com.impulso.impulsobackoffice.departamento.domain.port.in;

import java.util.List;

import com.impulso.impulsobackoffice.departamento.application.dto.DepartamentoResponseDto;
import com.impulso.impulsobackoffice.departamento.application.dto.MunicipioResponseDto;
import com.impulso.impulsobackoffice.departamento.infrastructure.exception.DepartmentDataNotFoundException;
import com.impulso.impulsobackoffice.departamento.infrastructure.exception.DepartmentExternalServiceException;

public interface RetrieveDepartamentoUseCasePort {
    List<DepartamentoResponseDto> getAllDepartamentos()
            throws DepartmentDataNotFoundException, DepartmentExternalServiceException;

    List<MunicipioResponseDto> getMunicipiosById(int id) throws DepartmentExternalServiceException;
}
