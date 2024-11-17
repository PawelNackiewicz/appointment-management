package pl.beaution.appointmentmanagement.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Pawel Nackiewicz",
                        email = "it.pnackiewicz@gmail.com"
                ),
                description = "API documentation for Beaution App. Beaution in application for appointment management.",
                title = "Open API specification - Beaution",
                version = "1.0"
        )
)
@SecurityScheme(
        name = "JWT auth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.COOKIE,
        paramName = "auth_by_cookie"
)
public class OpenApiConfig {
}
