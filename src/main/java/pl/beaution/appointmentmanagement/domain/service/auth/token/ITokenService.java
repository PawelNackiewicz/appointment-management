package pl.beaution.appointmentmanagement.domain.service.auth.token;

import jakarta.servlet.http.HttpServletResponse;
import pl.beaution.appointmentmanagement.domain.model.security.User;

public interface ITokenService {
    void addTokenToResponse(HttpServletResponse response, String token);

    String createToken(User user);

    boolean validateToken(String token, User userDto);

    String[] parseToken(String token);

    String calculateHmac(User user);
}
