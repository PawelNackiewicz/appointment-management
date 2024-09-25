package pl.beaution.appointmentmanagement.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.beaution.appointmentmanagement.domain.model.security.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
