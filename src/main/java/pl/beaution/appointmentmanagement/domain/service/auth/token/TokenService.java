package pl.beaution.appointmentmanagement.domain.service.auth.token;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.beaution.appointmentmanagement.domain.model.security.User;
import pl.beaution.appointmentmanagement.infrastructure.security.CookieAuthenticationFilter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Objects;

@Service
public class TokenService implements ITokenService {

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    public TokenService() {
    }

    @Override
    public void addTokenToResponse(HttpServletResponse response, String token) {
        Cookie authCookie = new Cookie(CookieAuthenticationFilter.COOKIE_NAME, token);
        authCookie.setHttpOnly(true);
        authCookie.setSecure(true);
        authCookie.setPath("/");
        authCookie.setMaxAge((int) Duration.of(1, ChronoUnit.DAYS).getSeconds());
        response.addCookie(authCookie);
    }

    @Override
    public String createToken(User user) {
        return user.getId() + "&" + user.getEmail() + "&" + calculateHmac(user);
    }

    @Override
    public String[] parseToken(String token) {
        return token.split("&");
    }

    @Override
    public boolean validateToken(String token, User user) {
        String[] parts = token.split("&");

        long userId = Long.parseLong(parts[0]);
        String email = parts[1];
        String hmac = parts[2];

        return hmac.equals(calculateHmac(user)) && userId == user.getId();
    }

    @Override
    public String calculateHmac(User user) {
        byte[] secretKeyBytes = Objects.requireNonNull(secretKey).getBytes(StandardCharsets.UTF_8);
        byte[] valueBytes = Objects.requireNonNull(user.getId() + "&" + user.getEmail()).getBytes(StandardCharsets.UTF_8);

        try {
            Mac mac = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, "HmacSHA512");
            mac.init(secretKeySpec);
            byte[] hmacBytes = mac.doFinal(valueBytes);
            return Base64.getEncoder().encodeToString(hmacBytes);

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}