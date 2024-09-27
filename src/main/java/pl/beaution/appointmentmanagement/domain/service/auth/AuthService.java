package pl.beaution.appointmentmanagement.domain.service.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.beaution.appointmentmanagement.application.dto.user.UserResponseDto;
import pl.beaution.appointmentmanagement.application.mapper.UserMapper;
import pl.beaution.appointmentmanagement.domain.model.security.User;
import pl.beaution.appointmentmanagement.domain.repository.UserRepository;
import pl.beaution.appointmentmanagement.domain.service.auth.password.PasswordService;
import pl.beaution.appointmentmanagement.domain.service.auth.token.TokenService;

import java.util.Arrays;
import java.util.Optional;

@Service
public class AuthService implements IAuthService{

    private final UserRepository userRepository;
    private final PasswordService passwordService;
    private final TokenService tokenService;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordService passwordService, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
        this.tokenService = tokenService;
    }
    @Override
    public String login(String login, String password) {
        User user = userRepository.findByEmail(login).orElseThrow(() -> new RuntimeException("User not found"));
        if (!passwordService.checkPassword(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        return tokenService.createToken(user);
    }

    @Override
    public UserResponseDto getLoggedInUser(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Optional<Cookie> cookieAuth = Arrays.stream(cookies)
                    .filter(cookie -> "auth_by_cookie".equals(cookie.getName()))
                    .findFirst();

            if (cookieAuth.isPresent()) {
                String token = cookieAuth.get().getValue();
                User user = findByToken(token);
                return UserMapper.toDto(user);
            }
        }
        throw new IllegalArgumentException("No valid token found in cookies");
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User findByToken(String token) {
        String[] parts = tokenService.parseToken(token);
        String email = parts[1];

        User user = findByEmail(email);

        if (!tokenService.validateToken(token, user)) {
            throw new RuntimeException("Invalid Token value");
        }

        return user;
    }
}
