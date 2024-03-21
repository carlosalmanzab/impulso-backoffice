package com.impulso.impulsobackoffice.usuario.infrastructure.repositories;

import java.util.Optional;
import java.util.UUID;

import com.impulso.impulsobackoffice.usuario.domain.model.Usuario;
import com.impulso.impulsobackoffice.usuario.domain.ports.out.UsuarioRepositoryPort;
import com.impulso.impulsobackoffice.usuario.infrastructure.repositories.entity.UsuarioEntity;

public class JpaUsuarioRepositoryAdapter implements UsuarioRepositoryPort {

    private final JpaUsuarioRepository jpaUsuarioRepository;

    public JpaUsuarioRepositoryAdapter(JpaUsuarioRepository jpaUsuarioRepository) {
        this.jpaUsuarioRepository = jpaUsuarioRepository;
    }

    /**
     * Save the provided Usuario and return the saved Usuario.
     *
     * @param usuario the Usuario to be saved
     * @return the saved Usuario
     */
    @Override
    public Usuario save(Usuario usuario) {
        UsuarioEntity usuarioEntity = UsuarioEntity.from(usuario);
        UsuarioEntity usuarioSave = jpaUsuarioRepository.save(usuarioEntity);
        return UsuarioEntity.toUsuario(usuarioSave);
    }

    /**
     * Find a Usuario by ID.
     *
     * @param id the UUID of the Usuario to find
     * @return an Optional containing the Usuario if found
     */
    @Override
    public Optional<Usuario> findById(UUID id) {
        return jpaUsuarioRepository.findById(id)
                .filter(e -> !e.isDeleted()).map(UsuarioEntity::toUsuario);
    }

    /**
     * Retrieves all the Usuario entities from the jpaUsuarioRepository and maps
     * them to Usuario objects.
     *
     * @return an iterable collection of Usuario objects representing all the
     *         Usuario entities.
     */
    @Override
    public Iterable<Usuario> findAll() {
        return jpaUsuarioRepository.findAll().stream()
                .filter(e -> !e.isDeleted())
                .map(UsuarioEntity::toUsuario)
                .toList();
    }

    /**
     * Updates the given Usuario object.
     *
     * @param usuario the Usuario object to be updated
     * @return an Optional containing the updated Usuario object, or empty if the
     *         update failed
     */
    @Override
    public Optional<Usuario> update(Usuario usuario) {
        if (!jpaUsuarioRepository.existsById(usuario.getId())) {
            return Optional.empty();
        }
        UsuarioEntity usuarioEntity = UsuarioEntity.from(usuario);
        UsuarioEntity usuarioUpdated = jpaUsuarioRepository.save(usuarioEntity);
        return Optional.of(UsuarioEntity.toUsuario(usuarioUpdated));

    }

    /**
     * Deletes a UsuarioEntity from the repository by its ID.
     *
     * @param id the UUID of the UsuarioEntity to be deleted
     * @return true if the UsuarioEntity was successfully deleted, false otherwise
     */
    @Override
    public boolean deleteById(UUID id) {
        Optional<UsuarioEntity> usuarioFound = jpaUsuarioRepository.findById(id);
        if (usuarioFound.isEmpty()) {
            return false;
        }
        usuarioFound.get().setDeleted(true);
        return jpaUsuarioRepository.save(usuarioFound.get()) != null;
    }

    /**
     * Checks if a user exists based on their identification number.
     *
     * @param identificacion the identification number of the user
     * @return true if a user with the given identification number exists, false
     *         otherwise
     */
    @Override
    public boolean existsByIdentificacion(int identificacion) {
        return jpaUsuarioRepository.existsByIdentificacion(identificacion);
    }

    /**
     * Find a Usuario by correoElectronico.
     *
     * @param correoElectronico the correoElectronico to search for
     * @return an Optional containing the Usuario, if found
     */
    @Override
    public Optional<Usuario> findBycorreoElectronico(String correoElectronico) {
        return jpaUsuarioRepository.findByCorreoElectronico(correoElectronico)
                .filter(e -> !e.isDeleted()).map(UsuarioEntity::toUsuario);
    }

}
