package com.impulso.impulsobackoffice.usuario.domain.ports.out;

import java.util.Optional;
import java.util.UUID;

import com.impulso.impulsobackoffice.usuario.domain.model.Usuario;

public interface UsuarioRepositoryPort {
    Usuario save(Usuario usuario);
    Optional<Usuario> findById(UUID id);
    Iterable<Usuario> findAll();
    Optional<Usuario> update(Usuario usuario);
    boolean existsByIdentificacion(int identificacion);
    boolean deleteById(UUID id);
    Optional<Usuario> findByCorreoElectronico(String correoElectronico);
}
