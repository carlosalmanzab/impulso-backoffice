package com.impulso.impulsobackoffice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication()
@ComponentScan(basePackages = { "com.impulso.impulsobackoffice.core", 
"com.impulso.impulsobackoffice.auth",
		"com.impulso.impulsobackoffice.usuario", "com.impulso.impulsobackoffice.usuario.infrastructure.controllers" })
@OpenAPIDefinition(info = @Info(title = "Report API", version = "1.0", description = "Report management API"))
public class ImpulsoBackofficeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImpulsoBackofficeApplication.class, args);
	}

}
