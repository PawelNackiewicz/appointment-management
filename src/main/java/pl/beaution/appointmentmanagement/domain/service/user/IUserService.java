package pl.beaution.appointmentmanagement.domain.service.user;

import pl.beaution.appointmentmanagement.domain.model.security.User;

public interface IUserService {
    User createUser(User user);

    User updateUser(User user) throws IllegalAccessException;

    User getUserById(Long id);

    void deleteUser(Long id);
}
