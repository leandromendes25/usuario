package com.leandromendes25.usuario.infrastructure.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SecurityScheme(name = SecurityConfig.SECURITY_SCHEME, type = SecuritySchemeType.HTTP,
bearerFormat = "jwt", scheme = "bearer")
public class SecurityConfig {
public static final String SECURITY_SCHEME = "bearerAuth";
}
