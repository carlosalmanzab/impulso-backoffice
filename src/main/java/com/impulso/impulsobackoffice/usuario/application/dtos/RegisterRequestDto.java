package com.impulso.impulsobackoffice.usuario.application.dtos;

import java.util.Date;

import com.impulso.impulsobackoffice.core.domain.enums.Roles;
import com.impulso.impulsobackoffice.core.domain.enums.TipoIdentificacion;
import com.impulso.impulsobackoffice.usuario.domain.model.Usuario;
import com.impulso.impulsobackoffice.usuario.domain.model.UsuarioBuilder;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequestDto(
        @NotEmpty(message = "El identificación es obligatoria")
        int identificacion,
        @NotEmpty(message = "El tipo de identificación es obligatorio")
        TipoIdentificacion tipoIdentificacion,
        @NotEmpty(message = "El primer nombre es obligatorio")
        String primerNombre,
        String segundoNombre,
        @NotEmpty(message = "El primer apellido es obligatorio")
        String primerApellido,
        String segundoApellido,
        @NotEmpty(message = "La fecha de nacimiento es obligatoria") @Past(message = "La fecha de nacimiento debe ser en el pasado") 
        Date fechaNacimiento,
        String numeroTelefono,
        String segundoNumeroTelefono,
        @NotEmpty(message = "El correo electronico es obligatorio, sera usado como su nombre de usuario") 
        String correoElectronico,
        Roles rol,
        @NotEmpty(message = "La contrasena es obligatoria")
        @Size(min = 8, max = 50, message = "La contrasena debe tener entre 8 y 50 caracteres")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
        message = "La contrasena debe contener al menos una letra mayúscula, una letra minúscula, un número y un caracter especial") 
        String password) {
    /**
     * Converts a RegisterRequestDto object to a Usuario object.
     *
     * @param registerRequest the RegisterRequestDto to convert
     * @return the created Usuario object
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
     * @param registerRequest description of the RegisterRequestDto parameter
     * @param password        description of the password parameter
     * @return a new Usuario object
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
