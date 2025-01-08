package pl.beaution.appointmentmanagement.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.beaution.appointmentmanagement.application.dto.user.UserResponseDto;
import pl.beaution.appointmentmanagement.application.mapper.UserMapper;
import pl.beaution.appointmentmanagement.domain.model.security.User;
import pl.beaution.appointmentmanagement.domain.service.auth.AuthService;
import pl.beaution.appointmentmanagement.domain.service.user.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Endpoints for manage users")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @Autowired
    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @Operation(
            summary = "Create user",
            description = "Creates new user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "Example user",
                                            value = "{\n" +
                                                    "  \"email\": \"test@mail.com\",\n" +
                                                    "  \"password\": \"password\",\n" +
                                                    "  \"firstName\": \"First Name\",\n" +
                                                    "  \"lastName\": \"Last Name\"\n" +
                                                    "}"
                                    )
                            }
                    )
            ),
            responses = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
        }
    )
    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                    .collect(Collectors.toList());

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        try {
            User createdUser = userService.createUser(user);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Get user",
            description = "Returns information about user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User found"),
                    @ApiResponse(responseCode = "500", description = "Cannot get user")
            }
    )
    @GetMapping("{id}")
    public ResponseEntity<?> getUser(@PathVariable String id) {
        //TODO add validation of requested user role
        try {
            Long userId = Long.valueOf(id);
            User user = userService.getUserById(userId);
            return new ResponseEntity<>(UserMapper.toDto(user), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Cannot get user");
        }
    }

    @Operation(
            summary = "Update user",
            description = "Updates user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "Example user",
                                            value = "{\n" +
                                                    "  \"email\": \"test@mail.com\",\n" +
                                                    "  \"password\": \"password\",\n" +
                                                    "  \"firstName\": \"First Name\",\n" +
                                                    "  \"lastName\": \"Last Name\"\n" +
                                                    "}"
                                    )
                            }
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "User updated successfully"),
                    @ApiResponse(responseCode = "500", description = "Cannot update user")
            }
    )
    @PatchMapping("{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody User user) {
        //TODO add validation of requested user role
        try {
            Long userId = Long.valueOf(id);
            User updatedUser = userService.updateUser(userId, user);
            return new ResponseEntity<>(UserMapper.toDto(updatedUser), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Cannot update user");
        }
    }
}
