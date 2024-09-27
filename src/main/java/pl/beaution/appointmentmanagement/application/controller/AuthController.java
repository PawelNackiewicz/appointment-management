package pl.beaution.appointmentmanagement.application.controller;

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
public class AuthController {
    private final AuthService authService;
    private final TokenService tokenService;
    @Autowired
    public AuthController(AuthService authService, TokenService tokenService) {
        this.authService = authService;
        this.tokenService = tokenService;
    }

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
