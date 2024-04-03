package com.impulso.impulsobackoffice.usuario.domain.ports.in;

import com.impulso.impulsobackoffice.auth.domain.model.Token;
import com.impulso.impulsobackoffice.usuario.application.dtos.RegisterRequestDto;

public interface RegisterUsuarioUseCasePort {

    Token register(final RegisterRequestDto RegisterRequest);
}
