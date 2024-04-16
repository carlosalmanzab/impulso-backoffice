package com.impulso.impulsobackoffice.departamento.application.dto;

import com.impulso.impulsobackoffice.departamento.domain.model.Municipio;

public record MunicipioResponseDto(int id, String nombre) {

    public static MunicipioResponseDto fromMunicipios(Municipio municipios) {
        return new MunicipioResponseDto(municipios.getId(), municipios.getNombre());
    }
}
