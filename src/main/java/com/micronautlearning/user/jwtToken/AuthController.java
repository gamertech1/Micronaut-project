package com.micronautlearning.user.jwtToken;

import com.micronautlearning.user.jwtToken.LoginRequest;
import io.micronaut.http.annotation.*;
import io.micronaut.http.HttpResponse;
import io.micronaut.openapi.visitor.security.SecurityRule;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.token.generator.TokenGenerator;
import io.micronaut.security.token.render.BearerAccessRefreshToken;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Map;
import java.util.Optional;
//@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/auth")
public class AuthController {

    @Inject
    TokenGenerator tokenGenerator;

    @Post("/login")
    public HttpResponse<?> login(@Body LoginRequest loginRequest) {
        if ("john".equals(loginRequest.username()) && "password".equals(loginRequest.password())) {
            Map<String, Object> claims = Map.of(
                    "sub", loginRequest.username(),             // subject
                    "roles", java.util.List.of("ROLE_USER"),    // roles
                    "iss", "my-app"                             // issuer
            );

            Optional<String> token = tokenGenerator.generateToken(claims);

            return token.map(accessToken ->
                    HttpResponse.ok(new BearerAccessRefreshToken(
                            loginRequest.username(),                // username
                            List.of("ROLE_USER"),                   // roles
                            3600,                                   // expiresIn (Integer)
                            accessToken,                            // accessToken
                            null,                                   // refreshToken (optional)
                            "Bearer"
                    ))
            ).orElse(HttpResponse.serverError());

        }

        return HttpResponse.unauthorized().body(Map.of("message","wrong password"));
    }
}
