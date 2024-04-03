package com.impulso.impulsobackoffice.usuario.infrastructure.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.impulso.impulsobackoffice.usuario.infrastructure.repositories.entity.UsuarioEntity;

@Repository
public interface JpaUsuarioRepository extends JpaRepository<UsuarioEntity, UUID> {

    boolean existsByIdentificacion(int identificacion);

    Optional<UsuarioEntity> findByCorreoElectronico(String correoElectronico);
}
