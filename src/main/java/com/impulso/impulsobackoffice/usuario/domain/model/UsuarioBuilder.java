package com.impulso.impulsobackoffice.usuario.domain.model;

import java.util.Date;
import java.util.UUID;

import com.impulso.impulsobackoffice.core.domain.enums.Roles;
import com.impulso.impulsobackoffice.core.domain.enums.TipoIdentificacion;

public class UsuarioBuilder {
    private UUID id;
    private int identificacion;
    private TipoIdentificacion tipoIdentificacion;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private Date fechaNacimiento;
    private String numeroTelefono;
    private String segundoNumeroTelefono;
    private String correoElectronico;
    private Roles rol;
    private String password;
    private Date fechaCreacion;
    private Date fechaActualizacion;

    public UsuarioBuilder id() {
        this.id = UUID.randomUUID();
        return this;
    }
    public UsuarioBuilder id(UUID id) {
        this.id = id;
        return this;
    }
    public UsuarioBuilder identificacion(int identificacion) {
        this.identificacion = identificacion;
        return this;
    }

    public UsuarioBuilder tipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
        return this;
    }

    public UsuarioBuilder primerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
        return this;
    }

    public UsuarioBuilder segundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
        return this;
    }

    public UsuarioBuilder primerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
        return this;
    }

    public UsuarioBuilder segundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
        return this;
    }

    public UsuarioBuilder fechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
        return this;
    }

    public UsuarioBuilder numeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
        return this;
    }

    public UsuarioBuilder segundoNumeroTelefono(String segundoNumeroTelefono) {
        this.segundoNumeroTelefono = segundoNumeroTelefono;
        return this;
    }

    public UsuarioBuilder correoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
        return this;
    }

    public UsuarioBuilder rol(Roles rol) {
        this.rol = rol;
        return this;
    }

    public UsuarioBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UsuarioBuilder fechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public UsuarioBuilder fechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
        return this;
    }

    public Usuario build() {
        return new Usuario(id, identificacion, tipoIdentificacion, primerNombre, segundoNombre, primerApellido,
                segundoApellido, fechaNacimiento, numeroTelefono, segundoNumeroTelefono, correoElectronico, rol, password,
                fechaCreacion, fechaActualizacion);
    }

    public UUID getId() {
        return id;
    }

    public int getIdentificacion() {
        return identificacion;
    }

    public TipoIdentificacion getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public String getSegundoNumeroTelefono() {
        return segundoNumeroTelefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public Roles getRol() {
        return rol;
    }

    public String getPassword() {
        return password;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }


}
