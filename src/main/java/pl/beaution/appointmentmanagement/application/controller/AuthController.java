package pl.beaution.appointmentmanagement.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.beaution.appointmentmanagement.application.dto.auth.LoginRequestDto;
import pl.beaution.appointmentmanagement.application.dto.user.UserResponseDto;
import pl.beaution.appointmentmanagement.domain.service.auth.AuthService;
import pl.beaution.appointmentmanagement.domain.service.auth.token.TokenService;

@RestController
@RequestMapping("/api/auth/sessions")
@Tag(name = "Auth", description = "Endpoints for user authentication")
public class AuthController {
    private final AuthService authService;
    private final TokenService tokenService;

    @Autowired
    public AuthController(AuthService authService, TokenService tokenService) {
        this.authService = authService;
        this.tokenService = tokenService;
    }

    @Operation(summary = "Login user", description = "Logs in user and returns JWT token",
    responses = {
        @ApiResponse(responseCode = "200", description = "User logged in successfully"),
        @ApiResponse(responseCode = "401", description = "Invalid credentials"),
        @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto loginRequest, HttpServletResponse response) {
        try {
            String token = authService.login(loginRequest.getEmail(), loginRequest.getPassword());
            tokenService.addTokenToResponse(response, token);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @Operation(summary = "Get logged in user", description = "Returns information about logged in user",
        responses = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "401", description = "User not authenticated")
        }
    )
    @SecurityRequirement(name = "bearer")
    @GetMapping("/me")
    public ResponseEntity<?> getLoggedInUser(HttpServletRequest request) {
        try {
            UserResponseDto user = authService.getLoggedInUser(request);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }
    }
}
