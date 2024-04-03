package com.impulso.impulsobackoffice.auth.domain.ports.in;

import com.impulso.impulsobackoffice.auth.domain.model.Token;
import com.impulso.impulsobackoffice.usuario.domain.model.Usuario;

public interface ValidateAuthenticationTokenUseCasePort {
 Usuario validToken(final Token token);
}
