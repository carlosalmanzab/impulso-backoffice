package com.impulso.impulsobackoffice.usuario.application.dtos;

import java.util.Date;

import com.impulso.impulsobackoffice.shared.enums.Roles;
import com.impulso.impulsobackoffice.shared.enums.TipoIdentificacion;
import com.impulso.impulsobackoffice.usuario.domain.model.Usuario;
import com.impulso.impulsobackoffice.usuario.domain.model.UsuarioBuilder;

public record RegisterRequestDto(
        int identificacion,
        TipoIdentificacion tipoIdentificacion,
        String primerNombre,
        String segundoNombre,
        String primerApellido,
        String segundoApellido,
        Date fechaNacimiento,
        String numeroTelefono,
        String segundoNumeroTelefono,
        String correoElectronico,
        Roles rol,
        String password) {

                /**
     * Converts a RegisterRequestDto object to a Usuario object.
     *
     * @param  registerRequest   the RegisterRequestDto to convert
     * @return                   the created Usuario object
     */
    public static Usuario toUsuario(RegisterRequestDto registerRequest) {
        return new UsuarioBuilder()
                .id()
                .identificacion(registerRequest.identificacion)
                .tipoIdentificacion(registerRequest.tipoIdentificacion)
                .primerNombre(registerRequest.primerNombre)
                .segundoNombre(registerRequest.segundoNombre)
                .primerApellido(registerRequest.primerApellido)
                .segundoApellido(registerRequest.segundoApellido)
                .fechaNacimiento(registerRequest.fechaNacimiento)
                .numeroTelefono(registerRequest.numeroTelefono)
                .segundoNumeroTelefono(registerRequest.segundoNumeroTelefono)
                .correoElectronico(registerRequest.correoElectronico)
                .rol(registerRequest.rol)
                .password(registerRequest.password)
                .build();
    }

        /**
     * Converts a RegisterRequestDto and a password into a Usuario object.
     *
     * @param  registerRequest	description of the RegisterRequestDto parameter
     * @param  password	        description of the password parameter
     * @return                  a new Usuario object
     */
    public static Usuario toUsuario(RegisterRequestDto registerRequest, String password) {
        return new UsuarioBuilder()
                .id()
                .identificacion(registerRequest.identificacion)
                .tipoIdentificacion(registerRequest.tipoIdentificacion)
                .primerNombre(registerRequest.primerNombre)
                .segundoNombre(registerRequest.segundoNombre)
                .primerApellido(registerRequest.primerApellido)
                .segundoApellido(registerRequest.segundoApellido)
                .fechaNacimiento(registerRequest.fechaNacimiento)
                .numeroTelefono(registerRequest.numeroTelefono)
                .segundoNumeroTelefono(registerRequest.segundoNumeroTelefono)
                .correoElectronico(registerRequest.correoElectronico)
                .rol(registerRequest.rol)
                .password(password)
                .build();
    }
}
