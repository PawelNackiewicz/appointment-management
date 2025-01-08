package pl.beaution.appointmentmanagement.domain.service.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pl.beaution.appointmentmanagement.domain.model.security.User;
import pl.beaution.appointmentmanagement.domain.repository.UserRepository;
import pl.beaution.appointmentmanagement.domain.service.auth.password.PasswordService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordService passwordService;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldCreateUser() {
        User user = new User();
        user.setEmail("test@email.com");
        user.setPassword("password");
        user.setFirstName("John");
        user.setLastName("Doe");

        User savedUser = new User();
        savedUser.setEmail("test@email.com");
        savedUser.setPassword("hashedpassword");
        savedUser.setFirstName("John");
        savedUser.setLastName("Doe");

        when(passwordService.hashPassword(user.getPassword())).thenReturn("hashedpassword");
        when(userRepository.save(user)).thenReturn(savedUser);
        User createdUser = userService.createUser(user);

        assertEquals(user.getEmail(), createdUser.getEmail());
        assertEquals(user.getFirstName(), createdUser.getFirstName());
        assertEquals(user.getLastName(), createdUser.getLastName());
        assertEquals(user.getPassword(), createdUser.getPassword());
    }

    @Test
    public void shouldUpdateUser() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setEmail("old@email.com");
        existingUser.setPassword("oldpassword");
        existingUser.setFirstName("Old");
        existingUser.setLastName("Name");

        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setEmail("new@email.com");
        updatedUser.setPassword("newpassword");
        updatedUser.setFirstName("New");
        updatedUser.setLastName("Name");

        when(passwordService.hashPassword("newpassword")).thenReturn("hashedPassword");

        when(userRepository.findById(existingUser.getId())).thenReturn(Optional.of(existingUser));

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            return invocation.<User>getArgument(0);
        });

        User result = userService.updateUser(updatedUser.getId(), updatedUser);

        assertEquals("new@email.com", result.getEmail());
        assertEquals("New", result.getFirstName());
        assertEquals("Name", result.getLastName());
        assertEquals("hashedPassword", result.getPassword());
    }

    @Test
    public void shouldThrowExceptionWhenUserToUpdateDoesNotExist() {
        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setEmail("new@email.com");

        when(userRepository.existsById(updatedUser.getId())).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(updatedUser.getId(), updatedUser));
    }

    @Test
    public void shouldGetUserById() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@email.com");

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));

        User result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getEmail(), result.getEmail());
    }

    @Test
    public void shouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.getUserById(1L));
        assertEquals("User with ID 1 not found.", exception.getMessage());
    }

    @Test
    public void shouldDeleteUser() {
        when(userRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> userService.deleteUser(1L));
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void shouldThrowExceptionWhenDeletingNonExistentUser() {
        when(userRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.deleteUser(1L));
        assertEquals("User with ID 1 does not exist.", exception.getMessage());
    }
}