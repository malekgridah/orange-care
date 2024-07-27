package com.billcom.bscs.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "${info.name}",
        description = "${info.description}", version = "${info.version}"))
@SecurityScheme(name = "bearerScheme", type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",scheme = "bearer")
public class SpringDocConfig {}
