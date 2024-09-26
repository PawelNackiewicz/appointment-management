package pl.beaution.appointmentmanagement.domain.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.beaution.appointmentmanagement.domain.model.security.User;
import pl.beaution.appointmentmanagement.domain.repository.UserRepository;
import pl.beaution.appointmentmanagement.domain.service.auth.password.PasswordService;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordService passwordService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }

    @Override
    public User createUser(User user) {
        try {
            String hashedPassword = passwordService.hashPassword(user.getPassword());
            user.setPassword(hashedPassword);
            return userRepository.save(user);
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not create user");
        }
    }

    @Override
    public User updateUser(User user) throws IllegalAccessException {
        return null;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + id + " not found."));
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User with ID " + id + " does not exist.");
        }
        userRepository.deleteById(id);
    }
}
