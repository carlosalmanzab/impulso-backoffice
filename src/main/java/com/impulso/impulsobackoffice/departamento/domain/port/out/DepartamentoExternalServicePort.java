package com.impulso.impulsobackoffice.departamento.domain.port.out;

import java.util.List;

import com.impulso.impulsobackoffice.departamento.domain.model.Departamento;
import com.impulso.impulsobackoffice.departamento.domain.model.Municipio;
import com.impulso.impulsobackoffice.departamento.infrastructure.exception.DepartmentDataNotFoundException;
import com.impulso.impulsobackoffice.departamento.infrastructure.exception.DepartmentExternalServiceException;

public interface DepartamentoExternalServicePort {

    List<Departamento> getDepartamentos()
            throws DepartmentDataNotFoundException, DepartmentExternalServiceException;

    List<Municipio> getMunicipiosByDepartamentoId(int id) throws DepartmentExternalServiceException;
}
