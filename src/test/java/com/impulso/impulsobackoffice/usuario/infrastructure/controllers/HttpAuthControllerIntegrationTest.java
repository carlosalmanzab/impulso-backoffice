package com.impulso.impulsobackoffice.usuario.infrastructure.controllers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.impulso.impulsobackoffice.ImpulsoBackofficeApplication;
import com.impulso.impulsobackoffice.auth.domain.model.Token;
import com.impulso.impulsobackoffice.auth.domain.ports.in.ValidateAuthenticationTokenUseCasePort;
import com.impulso.impulsobackoffice.core.domain.enums.Roles;
import com.impulso.impulsobackoffice.core.domain.enums.TipoIdentificacion;
import com.impulso.impulsobackoffice.usuario.application.dtos.LoginRequestDto;
import com.impulso.impulsobackoffice.usuario.application.dtos.RegisterRequestDto;
import com.impulso.impulsobackoffice.usuario.domain.model.Usuario;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = ImpulsoBackofficeApplication.class)
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class HttpAuthControllerIntegrationTest {
        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private ValidateAuthenticationTokenUseCasePort validateAuthenticationToken;

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

        @DisplayName("Integration test for register by http")
        @Test
        @Order(1)
        void testRegister() throws JsonProcessingException, Exception {
                final RegisterRequestDto registerRequestDto = this.testRegisterRequestDto;

                ResultActions response = mockMvc.perform(
                                MockMvcRequestBuilders.post("/auth/register")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(registerRequestDto)));

                response.andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.token").isNotEmpty());

                String json = response.andReturn().getResponse().getContentAsString();

                @SuppressWarnings("unchecked")
                Map<String, Object> jsonMap = objectMapper.readValue(json, Map.class);

                Token token = new Token(jsonMap.get("token").toString());
                assertNotNull(token);

                Usuario user = validateAuthenticationToken.validToken(token);
                assertNotNull(user);
        }

        @DisplayName("Integration test for register existing usuario by http")
        @Test
        @Order(2)
        void testRegisterExisting() throws JsonProcessingException, Exception {
                final RegisterRequestDto registerRequestDto = this.testRegisterRequestDto;

                ResultActions response = mockMvc.perform(
                                MockMvcRequestBuilders.post("/auth/register")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(registerRequestDto)));
                response.andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isConflict());
        }

        @DisplayName("Integration test for login by http")
        @Test
        @Order(3)
        void testLogin() throws JsonProcessingException, Exception {
                final LoginRequestDto loginRequestDto = this.testLoginRequestDto;

                ResultActions response = mockMvc.perform(
                                MockMvcRequestBuilders.post("/auth/login")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(loginRequestDto)));
                response.andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.token").isNotEmpty());
                String json = response.andReturn().getResponse().getContentAsString();

                @SuppressWarnings("unchecked")
                Map<String, Object> jsonMap = objectMapper.readValue(json, Map.class);
                Token token = new Token(jsonMap.get("token").toString());
                assertNotNull(token);

                Usuario user = validateAuthenticationToken.validToken(token);
                assertNotNull(user);
        }

        @DisplayName("Integration test for invalid login by http")
        @Test
        @Order(4)
        void testInvalidLogin() throws JsonProcessingException, Exception {

                final LoginRequestDto loginRequestDto = new LoginRequestDto(
                                "InvalidUsername@fakemail.com", testLoginRequestDto.password());

                ResultActions response = mockMvc.perform(
                                MockMvcRequestBuilders.post("/auth/login")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(loginRequestDto)));
                response.andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
        }

        @DisplayName("Integration test login for invalid password by http")
        @Test
        @Order(5)
        void testLoginInvalidPassword() throws JsonProcessingException, Exception {
                final LoginRequestDto loginRequestDto = new LoginRequestDto(
                                testLoginRequestDto.username(),
                                "InvalidPassword");

                ResultActions response = mockMvc.perform(
                                MockMvcRequestBuilders.post("/auth/login")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(loginRequestDto)));

                response.andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
        }
}
