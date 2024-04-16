package com.impulso.impulsobackoffice.departamento.application.dto;

import java.util.List;

import com.impulso.impulsobackoffice.departamento.domain.model.Departamento;

public record DepartamentoResponseDto(
        int id,
        String nombre,
        List<MunicipioResponseDto> municipios) {

    public static DepartamentoResponseDto fromDepartamento(Departamento departamento) {
                return new DepartamentoResponseDto(
                        departamento.getId(),
                        departamento.getNombre(),
                        departamento.getMunicipios().stream().map(MunicipioResponseDto::fromMunicipios).toList()
                );
            }
}
