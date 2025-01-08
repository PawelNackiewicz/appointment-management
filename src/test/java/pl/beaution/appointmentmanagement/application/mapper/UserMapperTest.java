package pl.beaution.appointmentmanagement.application.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.beaution.appointmentmanagement.domain.model.security.User;
import pl.beaution.appointmentmanagement.domain.repository.UserRepository;
import pl.beaution.appointmentmanagement.domain.service.auth.password.PasswordService;
import pl.beaution.appointmentmanagement.domain.service.user.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserMapperTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordService passwordService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateUserSuccessfully() {
        // Arrange
        User user = new User();
        user.setPassword("plainPassword");
        User savedUser = new User();
        savedUser.setPassword("hashedPassword");

        when(passwordService.hashPassword("plainPassword")).thenReturn("hashedPassword");
        when(userRepository.save(user)).thenReturn(savedUser);

        // Act
        User result = userService.createUser(user);

        // Assert
        assertNotNull(result);
        assertEquals("hashedPassword", result.getPassword());
        verify(passwordService, times(1)).hashPassword("plainPassword");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void shouldThrowExceptionWhenCreateUserFails() {
        // Arrange
        User user = new User();
        user.setPassword("plainPassword");

        when(passwordService.hashPassword("plainPassword")).thenThrow(new RuntimeException("Hashing failed"));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));
        assertEquals("Could not create user", exception.getMessage());

        verify(passwordService, times(1)).hashPassword("plainPassword");
        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldGetUserByIdWhenExists() {
        // Arrange
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        User result = userService.getUserById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundById() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.getUserById(1L));
        assertEquals("User with ID 1 not found.", exception.getMessage());

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void shouldDeleteUserWhenExists() {
        // Arrange
        when(userRepository.existsById(1L)).thenReturn(true);

        // Act
        userService.deleteUser(1L);

        // Assert
        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentUser() {
        // Arrange
        when(userRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.deleteUser(1L));
        assertEquals("User with ID 1 does not exist.", exception.getMessage());

        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, never()).deleteById(anyLong());
    }
}