package pl.beaution.appointmentmanagement.domain.service.user;

import pl.beaution.appointmentmanagement.domain.model.security.User;

public interface IUserService {
    User createUser(User user);

    User updateUser(Long id, User user) throws IllegalArgumentException;

    User getUserById(Long id);

    void deleteUser(Long id);
}
