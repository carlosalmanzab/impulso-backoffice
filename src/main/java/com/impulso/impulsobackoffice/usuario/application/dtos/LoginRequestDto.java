package com.impulso.impulsobackoffice.usuario.application.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDto(  
@NotBlank    
@Size(min = 10, max = 50)
@Email
String username, 
@NotBlank
@Size(min = 8, max = 50)
String password) {}