package pl.beaution.appointmentmanagement.domain.service.auth;

import jakarta.servlet.http.HttpServletRequest;
import pl.beaution.appointmentmanagement.application.dto.user.UserResponseDto;

public interface IAuthService {
    String login(String username, String password);

    UserResponseDto getLoggedInUser(HttpServletRequest request);
}
