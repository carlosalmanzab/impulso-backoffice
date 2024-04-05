package com.impulso.impulsobackoffice.usuario.domain.in;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com.impulso.impulsobackoffice.auth.domain.model.Token;
import com.impulso.impulsobackoffice.auth.domain.ports.in.CreateAuthenticationTokenUseCasePort;
import com.impulso.impulsobackoffice.core.domain.enums.Roles;
import com.impulso.impulsobackoffice.core.domain.enums.TipoIdentificacion;
import com.impulso.impulsobackoffice.usuario.application.dtos.RegisterRequestDto;
import com.impulso.impulsobackoffice.usuario.application.exceptions.UsuarioAlreadyExistsException;
import com.impulso.impulsobackoffice.usuario.application.usecase.RegisterUsuarioUseCase;
import com.impulso.impulsobackoffice.usuario.domain.model.Usuario;
import com.impulso.impulsobackoffice.usuario.domain.ports.in.RegisterUsuarioUseCasePort;
import com.impulso.impulsobackoffice.usuario.domain.ports.out.UsuarioRepositoryPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class RegisterUsuarioUseCaseTest {

    @Autowired
    private RegisterUsuarioUseCasePort registerUsuarioUseCase;

    @MockBean
    private UsuarioRepositoryPort usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CreateAuthenticationTokenUseCasePort createAuthenticationToken;

    @BeforeEach
    public void setUp() {
        registerUsuarioUseCase = new RegisterUsuarioUseCase(
                createAuthenticationToken,
                usuarioRepository,
                passwordEncoder);
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
                .fechaNacimiento(Date.from(LocalDate.of(1990, 1, 1).atStartOfDay().toInstant(ZoneOffset.UTC)))
                .numeroTelefono("123456789")
                .segundoNumeroTelefono("987654321")
                .correoElectronico("lOqZB@example.com")
                .rol(Roles.USER)
                .password("P@ssw0rd")
                .build();
        return usuario;
    }

    @DisplayName("Test register usuario")
    @Test
    void testRegisterUsuario() throws UsuarioAlreadyExistsException {
        // GIVEN
        final Usuario usuario = testUsuario();
        RegisterRequestDto registerRequestDto = new RegisterRequestDto(usuario.getIdentificacion(),
                usuario.getTipoIdentificacion(),
                usuario.getPrimerNombre(),
                usuario.getSegundoNombre(),
                usuario.getPrimerApellido(),
                usuario.getSegundoApellido(),
                usuario.getFechaNacimiento(),
                usuario.getNumeroTelefono(),
                usuario.getSegundoNumeroTelefono(),
                usuario.getCorreoElectronico(),
                usuario.getRol(),
                usuario.getPassword());

        // WHEN
        when(usuarioRepository.findByCorreoElectronico(usuario.getCorreoElectronico())).thenReturn(Optional.empty());
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Token token = registerUsuarioUseCase.register(registerRequestDto);
        // THEN
        String[] tokenPieces = token.getToken().split("\\.");
        assertThat(token).isNotNull();
        assertThat(token.getToken()).isNotEmpty();
        assertThat(tokenPieces).hasSize(3);
    }

    @DisplayName("Test register usuario when user already exists")
    @Test
    void testRegisterUsuarioWhenUserAlreadyExists() throws UsuarioAlreadyExistsException {
        // GIVEN
        final Usuario usuario = testUsuario();
        RegisterRequestDto registerRequestDto = new RegisterRequestDto(
                usuario.getIdentificacion(),
                usuario.getTipoIdentificacion(),
                usuario.getPrimerNombre(),
                usuario.getSegundoNombre(),
                usuario.getPrimerApellido(),
                usuario.getSegundoApellido(),
                usuario.getFechaNacimiento(),
                usuario.getNumeroTelefono(),
                usuario.getSegundoNumeroTelefono(),
                usuario.getCorreoElectronico(),
                usuario.getRol(),
                usuario.getPassword());
        // WHEN
        when(usuarioRepository.findByCorreoElectronico(registerRequestDto.correoElectronico()))
                .thenReturn(Optional.of(usuario));

        // THEN
        assertThatExceptionOfType(UsuarioAlreadyExistsException.class).isThrownBy(
                () -> registerUsuarioUseCase.register(registerRequestDto));
    }

}
