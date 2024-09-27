package pl.beaution.appointmentmanagement.application.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.beaution.appointmentmanagement.application.dto.salon.SalonResponseDto;
import pl.beaution.appointmentmanagement.application.dto.user.UserResponseDto;
import pl.beaution.appointmentmanagement.domain.model.Salon;
import pl.beaution.appointmentmanagement.domain.service.auth.AuthService;
import pl.beaution.appointmentmanagement.domain.service.salon.SalonService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/salons")
public class SalonController {

    private final SalonService salonService;
    private final AuthService authService;

    @Autowired
    public SalonController(SalonService salonService, AuthService authService) {
        this.salonService = salonService;
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<?> createSalon(@Valid @RequestBody Salon salon, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                    .collect(Collectors.toList());

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        try {
            UserResponseDto user = authService.getLoggedInUser(request);
            Salon createdSalon = salonService.createSalonForUser(user.getId(), salon);
            return new ResponseEntity<>(createdSalon, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<List<SalonResponseDto>> getUserSalons(HttpServletRequest request) {
        try {
            UserResponseDto user = authService.getLoggedInUser(request);
            List<SalonResponseDto> salons = salonService.findSalonsByUserId(user.getId());
            return new ResponseEntity<>(salons, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
