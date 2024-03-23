package com.impulso.impulsobackoffice.usuario.domain.model;

import java.util.Date;
import java.util.UUID;

import com.impulso.impulsobackoffice.core.domain.enums.Roles;
import com.impulso.impulsobackoffice.core.domain.enums.TipoIdentificacion;

public class Usuario {
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

    public Usuario(UUID id, int identificacion, TipoIdentificacion tipoIdentificacion, String primerNombre,
            String segundoNombre, String primerApellido, String segundoApellido, Date fechaNacimiento,
            String numeroTelefono, String segundoNumeroTelefono, String correoElectronico, Roles rol, String password,
            Date fechaCreacion, Date fechaActualizacion) {
        this.id = id;
        this.identificacion = identificacion;
        this.tipoIdentificacion = tipoIdentificacion;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.fechaNacimiento = fechaNacimiento;
        this.numeroTelefono = numeroTelefono;
        this.segundoNumeroTelefono = segundoNumeroTelefono;
        this.correoElectronico = correoElectronico;
        this.rol = rol;
        this.password = password;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(int identificacion) {
        this.identificacion = identificacion;
    }

    public TipoIdentificacion getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getSegundoNumeroTelefono() {
        return segundoNumeroTelefono;
    }

    public void setSegundoNumeroTelefono(String segundoNumeroTelefono) {
        this.segundoNumeroTelefono = segundoNumeroTelefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public Roles getRol() {
        return rol;
    }

    public void setRol(Roles rol) {
        this.rol = rol;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public static UsuarioBuilder builder() {
        return new UsuarioBuilder();
    }

}
