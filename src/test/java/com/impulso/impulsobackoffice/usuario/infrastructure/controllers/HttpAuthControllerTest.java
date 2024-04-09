package com.impulso.impulsobackoffice.usuario.infrastructure.controllers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.impulso.impulsobackoffice.auth.domain.model.Token;
import com.impulso.impulsobackoffice.auth.domain.ports.in.CreateAuthenticationTokenUseCasePort;
import com.impulso.impulsobackoffice.core.domain.enums.Roles;
import com.impulso.impulsobackoffice.core.domain.enums.TipoIdentificacion;
import com.impulso.impulsobackoffice.usuario.application.dtos.LoginRequestDto;
import com.impulso.impulsobackoffice.usuario.application.dtos.RegisterRequestDto;
import com.impulso.impulsobackoffice.usuario.application.exceptions.InvalidAuthenticationLoginException;
import com.impulso.impulsobackoffice.usuario.application.exceptions.InvalidAuthenticationPasswordException;
import com.impulso.impulsobackoffice.usuario.application.exceptions.UsuarioAlreadyExistsException;
import com.impulso.impulsobackoffice.usuario.domain.ports.in.LoginUsuarioUseCasePort;
import com.impulso.impulsobackoffice.usuario.domain.ports.in.RegisterUsuarioUseCasePort;
import com.impulso.impulsobackoffice.usuario.domain.ports.out.UsuarioRepositoryPort;


@WebMvcTest(controllers = HttpAuthController.class)
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ActiveProfiles("test")
class HttpAuthControllerTest {
        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private RegisterUsuarioUseCasePort registerUsuarioUseCase;

        @MockBean
        private LoginUsuarioUseCasePort loginUsuarioUseCase;

        @MockBean
        private UsuarioRepositoryPort usuarioRepository;

        @MockBean
        private PasswordEncoder passwordEncoder;

        @MockBean
        private CreateAuthenticationTokenUseCasePort createAuthenticationToken;

        @Autowired
        private ObjectMapper objectMapper;

        private RegisterRequestDto testRegisterRequestDto;

        private LoginRequestDto testLoginRequestDto;

        @BeforeEach
        public void setUp() {
                testRegisterRequestDto = new RegisterRequestDto(
                                1009898987,
                                TipoIdentificacion.CEDULA,
                                "firstName",
                                "secondName",
                                "lastName",
                                "secondLastName",
                                Date.from(LocalDate.of(1990, 1, 1).atTime(0, 0).toInstant(ZoneOffset.UTC)),
                                "123456789",
                                "987654321",
                                "lOqZB@example.com",
                                Roles.USER,
                                "P@ssw0rd");

                testLoginRequestDto = new LoginRequestDto("lOqZB@example.com", "P@ssw0rd");
        }

        @DisplayName("test register usuario by http")
        @Test
        void testRegister() throws Exception {
                // given
                RegisterRequestDto registerRequestDto = testRegisterRequestDto;

                Token mockedToken = Mockito.mock(Token.class);
                BDDMockito.given(mockedToken.getToken()).willReturn("mocked_token_value");

                BDDMockito.given(registerUsuarioUseCase.register(ArgumentMatchers.any(RegisterRequestDto.class)))
                                .willReturn(mockedToken);
                // when
                ResultActions reponse = mockMvc.perform(
                                MockMvcRequestBuilders.post("/auth/register")
                                                .with(csrf())
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(registerRequestDto)));
                // then
                reponse.andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.token")
                                                .value("mocked_token_value"));
        }

        @DisplayName("test register existing usuario by http")
        @Test
        void testRegisterExisting() throws Exception {
                // given
                RegisterRequestDto registerRequestDto = testRegisterRequestDto;
                BDDMockito.given(registerUsuarioUseCase.register(ArgumentMatchers.any(RegisterRequestDto.class)))
                                .willThrow(new UsuarioAlreadyExistsException(registerRequestDto.correoElectronico()));
                // when
                ResultActions reponse = mockMvc.perform(
                                MockMvcRequestBuilders.post("/auth/register")
                                                .with(csrf())
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(registerRequestDto)));
                // then
                reponse.andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isConflict());
        }

        @DisplayName("test login usuario by http")
        @Test
        void testLogin() throws Exception {
                // given
                LoginRequestDto loginRequestDto = testLoginRequestDto;
                Token mockedToken = Mockito.mock(Token.class);
                BDDMockito.given(mockedToken.getToken()).willReturn("mocked_token_value");
                BDDMockito.given(loginUsuarioUseCase.login(ArgumentMatchers.any(LoginRequestDto.class)))
                                .willReturn(mockedToken);
                // when
                ResultActions reponse = mockMvc.perform(
                                MockMvcRequestBuilders.post("/auth/login")
                                                .with(csrf())
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(loginRequestDto)));
                // then
                reponse.andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.token")
                                                .value("mocked_token_value"));
        }

        @DisplayName("test login invalid usuario by http")
        @Test
        void testLoginInvalid() throws Exception {
                // given
                LoginRequestDto loginRequestDto = testLoginRequestDto;
                BDDMockito.given(loginUsuarioUseCase.login(ArgumentMatchers.any(LoginRequestDto.class)))
                                .willThrow(new InvalidAuthenticationLoginException(loginRequestDto.username()));
                // when
                ResultActions reponse = mockMvc.perform(
                                MockMvcRequestBuilders.post("/auth/login")
                                                .with(csrf())
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(loginRequestDto)));
                // then
                reponse.andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
        }

        @DisplayName("test login invalid password by http")
        @Test
        void testLoginInvalidPassword() throws Exception {
                // given
                LoginRequestDto loginRequestDto = testLoginRequestDto;
                BDDMockito.given(loginUsuarioUseCase.login(ArgumentMatchers.any(LoginRequestDto.class)))
                                .willThrow(new InvalidAuthenticationPasswordException(loginRequestDto.username()));
                // when
                ResultActions reponse = mockMvc.perform(
                                MockMvcRequestBuilders.post("/auth/login")
                                                .with(csrf())
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(loginRequestDto)));
                // then
                reponse.andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
        }

}
