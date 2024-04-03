package com.impulso.impulsobackoffice.usuario.domain.in;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com.impulso.impulsobackoffice.auth.domain.model.Token;
import com.impulso.impulsobackoffice.auth.domain.ports.in.CreateAuthenticationTokenUseCasePort;
import com.impulso.impulsobackoffice.core.domain.enums.Roles;
import com.impulso.impulsobackoffice.core.domain.enums.TipoIdentificacion;
import com.impulso.impulsobackoffice.usuario.application.dtos.LoginRequestDto;
import com.impulso.impulsobackoffice.usuario.application.exceptions.InvalidAuthenticationLoginException;
import com.impulso.impulsobackoffice.usuario.application.exceptions.InvalidAuthenticationPasswordException;
import com.impulso.impulsobackoffice.usuario.application.usecase.LoginUsuarioUseCase;
import com.impulso.impulsobackoffice.usuario.domain.model.Usuario;
import com.impulso.impulsobackoffice.usuario.domain.ports.in.LoginUsuarioUseCasePort;
import com.impulso.impulsobackoffice.usuario.domain.ports.out.UsuarioRepositoryPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"dev", "test"})
class TestLoginUsuarioUseCase {

    @Autowired
    private LoginUsuarioUseCasePort loginUsuarioUseCase;

    @MockBean
    private UsuarioRepositoryPort usuarioRepositoryPort;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CreateAuthenticationTokenUseCasePort createAuthenticationToken;

    @BeforeEach
    public void setUp() {
        loginUsuarioUseCase = new LoginUsuarioUseCase(
                usuarioRepositoryPort,
                passwordEncoder,
                createAuthenticationToken);
    }

    private Usuario testUsuario() {
        Usuario usuario = Usuario.builder()
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
        assertThat(loginUsuarioUseCase).isNotNull();
    }

    @Test
    @DisplayName("Test login usuario")
    void testLoginUsuario() throws InvalidAuthenticationLoginException, InvalidAuthenticationPasswordException {
        // GIVEN
        final Usuario usuarioOb = testUsuario();
        Usuario usuarioEncode = usuarioOb;
        usuarioEncode.setPassword(passwordEncoder.encode(usuarioEncode.getPassword()));

        Optional<Usuario> usuario = Optional.of(usuarioEncode);
        LoginRequestDto loginRequestDto = new LoginRequestDto(usuarioEncode.getCorreoElectronico(),
                testUsuario().getPassword());
        // WHEN
        when(usuarioRepositoryPort.findByCorreoElectronico(usuarioEncode.getCorreoElectronico())).thenReturn(usuario);
        Token token = loginUsuarioUseCase.login(loginRequestDto);
        // THEN
        String[] tokenPieces = token.getToken().split("\\.");
        assertThat(token).isNotNull();
        assertThat(token.getToken()).isNotEmpty();
        assertThat(tokenPieces).hasSize(3);
    }

    @Test
    @DisplayName("Test invalid authentication login")
    void testInvalidAuthenticationLogin()
            throws InvalidAuthenticationLoginException, InvalidAuthenticationPasswordException {
        // GIVEN
        final String login = "invalidLogin";
        final String password = "invalidPassword";
        LoginRequestDto loginRequestDto = new LoginRequestDto(login, password);
        // WHEN
        when(usuarioRepositoryPort.findByCorreoElectronico(login)).thenReturn(Optional.empty());
        // THEN
        assertThatExceptionOfType(InvalidAuthenticationLoginException.class)
                .isThrownBy(() -> loginUsuarioUseCase.login(loginRequestDto));
    }

    @Test
    @DisplayName("Test invalid authentication password")
    void testInvalidAuthenticationPassword()
            throws InvalidAuthenticationLoginException, InvalidAuthenticationPasswordException {
        // GIVEN
        final Usuario testUser = testUsuario();
        final String login = testUser.getCorreoElectronico();
        final String password = "invalidPassword";
        LoginRequestDto loginRequestDto = new LoginRequestDto(login, password);
        // WHEN
        when(usuarioRepositoryPort.findByCorreoElectronico(login)).thenReturn(Optional.of(testUser));
        // THEN

        assertThatExceptionOfType(InvalidAuthenticationPasswordException.class)
                .isThrownBy(() -> loginUsuarioUseCase.login(loginRequestDto));
    }
}
