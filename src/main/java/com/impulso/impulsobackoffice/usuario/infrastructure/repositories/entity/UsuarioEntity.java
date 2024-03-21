package com.impulso.impulsobackoffice.usuario.infrastructure.repositories.entity;

import java.util.Date;
import java.util.UUID;

import com.impulso.impulsobackoffice.shared.enums.Roles;
import com.impulso.impulsobackoffice.shared.enums.TipoIdentificacion;
import com.impulso.impulsobackoffice.usuario.domain.model.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "usuario", schema = "seguridad", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "identificacion", "numero_telefono", "correo_electronico" })
})
public class UsuarioEntity {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "identificacion", unique = true)
    private int identificacion;

    @Column(name = "tipo_identificacion")
    @Enumerated(EnumType.STRING)
    private TipoIdentificacion tipoIdentificacion;

    @Column(name = "primer_nombre")
    private String primerNombre;

    @Column(name = "segundo_nombre")
    private String segundoNombre;

    @Column(name = "primer_apellido")
    private String primerApellido;

    @Column(name = "segundo_apellido")
    private String segundoApellido;

    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;

    @Column(name = "numero_telefono", unique = true)
    private String numeroTelefono;

    @Column(name = "segundo_numero_telefono")
    private String segundoNumeroTelefono;

    @Column(name = "correo_electronico", unique = true)
    private String correoElectronico;

    @Column(name = "rol")
    @Enumerated(EnumType.STRING)
    private Roles rol;

    @Column(name = "password")
    private String password;

    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private Date fechaActualizacion;

    @Column(name = "eliminado")
    private boolean isDeleted;

    public static UsuarioEntity from(Usuario usuario) {
        return UsuarioEntity.builder()
                .id(usuario.getId())
                .identificacion(usuario.getIdentificacion())
                .tipoIdentificacion(usuario.getTipoIdentificacion())
                .primerNombre(usuario.getPrimerNombre())
                .segundoNombre(usuario.getSegundoNombre())
                .primerApellido(usuario.getPrimerApellido())
                .segundoApellido(usuario.getSegundoApellido())
                .fechaNacimiento(usuario.getFechaNacimiento())
                .numeroTelefono(usuario.getNumeroTelefono())
                .segundoNumeroTelefono(usuario.getSegundoNumeroTelefono())
                .correoElectronico(usuario.getCorreoElectronico())
                .rol(usuario.getRol())
                .password(usuario.getPassword())
                .build();
    }

    public static Usuario toUsuario(UsuarioEntity usuarioEntity) {
        return Usuario.builder()
                .id(usuarioEntity.getId())
                .identificacion(usuarioEntity.getIdentificacion())
                .tipoIdentificacion(usuarioEntity.getTipoIdentificacion())
                .primerNombre(usuarioEntity.getPrimerNombre())
                .segundoNombre(usuarioEntity.getSegundoNombre())
                .primerApellido(usuarioEntity.getPrimerApellido())
                .segundoApellido(usuarioEntity.getSegundoApellido())
                .fechaNacimiento(usuarioEntity.getFechaNacimiento())
                .numeroTelefono(usuarioEntity.getNumeroTelefono())
                .segundoNumeroTelefono(usuarioEntity.getSegundoNumeroTelefono())
                .correoElectronico(usuarioEntity.getCorreoElectronico())
                .rol(usuarioEntity.getRol())
                .password(usuarioEntity.getPassword())
                .fechaCreacion(usuarioEntity.getFechaCreacion())
                .fechaActualizacion(usuarioEntity.getFechaActualizacion())
                .build();
    }
}
