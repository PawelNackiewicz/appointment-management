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

import static org.junit.jupiter.api.Assertions.*;
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
}