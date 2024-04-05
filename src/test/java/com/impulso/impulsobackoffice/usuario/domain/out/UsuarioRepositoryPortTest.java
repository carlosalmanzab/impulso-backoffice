package com.impulso.impulsobackoffice.usuario.domain.out;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import com.impulso.impulsobackoffice.core.domain.enums.Roles;
import com.impulso.impulsobackoffice.core.domain.enums.TipoIdentificacion;
import com.impulso.impulsobackoffice.usuario.domain.model.Usuario;
import com.impulso.impulsobackoffice.usuario.domain.ports.out.UsuarioRepositoryPort;

import jakarta.transaction.Transactional;

@DisplayName("Test JpaUsuarioRepositoryAdapter")
@SpringBootTest
@ActiveProfiles("test")
class UsuarioRepositoryPortTest {

    @Autowired
    private UsuarioRepositoryPort usuarioRepository;

    private Usuario testUsuario() {
        final Usuario usuario = Usuario.builder()
                .id(UUID.randomUUID())
                .identificacion(1009898987)
                .tipoIdentificacion(TipoIdentificacion.CEDULA)
                .primerNombre("firstName")
                .segundoNombre("secondName")
                .primerApellido("lastName")
                .segundoApellido("secondLastName")
                .fechaNacimiento(new Date())
                .numeroTelefono("123456789")
                .segundoNumeroTelefono("987654321")
                .correoElectronico("lOqZB@example.com")
                .rol(Roles.USER)
                .password("password")
                .build();

        return usuario;
    }

    @Test
    @DisplayName("Test injected components are not null")
    void testInjectedComponentsAreNotNull() {
        assertThat(usuarioRepository).isNotNull();
    }

    @DisplayName("Test save usuario")
    @Test
    @Transactional
    @Rollback
    void testSaveUsuario() {
        // GIVEN
        final Usuario usuario = testUsuario();
        // WHEN
        Usuario usuarioSaved = usuarioRepository.save(usuario);
        // THEN
        assertThat(usuarioSaved.getId()).isEqualTo(usuario.getId());
        assertThat(usuarioSaved.getTipoIdentificacion()).isEqualTo(TipoIdentificacion.CEDULA);
        assertThat(usuarioSaved.getRol()).isEqualTo(Roles.USER);
    }

    @DisplayName("Test find usuario by email")
    @Test
    @Transactional
    @Rollback
    void testFindUsuarioByEmail() {
        // GIVEN
        final Usuario usuario = testUsuario();

        usuarioRepository.save(usuario);
        // WHEN
        Optional<Usuario> usuarioFound = usuarioRepository
                .findByCorreoElectronico(usuario.getCorreoElectronico());
        // THEN
        assertThat(usuarioFound).isPresent();
        assertThat(usuarioFound.get().getId()).isNotNull();
        assertThat(usuarioFound.get().getCorreoElectronico()).isEqualTo(usuario.getCorreoElectronico());
    }

    @DisplayName("test exist ByIdentification")
    @Test
    @Transactional
    @Rollback
    void testExistByIdentification() {
        // GIVEN
        final Usuario usuario = testUsuario();
        usuarioRepository.save(usuario);
        // WHEN
        boolean exist = usuarioRepository.existsByIdentificacion(usuario.getIdentificacion());
        // THEN
        assertThat(exist).isTrue();
    }

}
