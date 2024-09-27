package pl.beaution.appointmentmanagement.domain.service.auth.password;

public interface IPasswordService {
    String hashPassword(String plainPassword);

    boolean checkPassword(String plainPassword, String hashedPassword);
}
