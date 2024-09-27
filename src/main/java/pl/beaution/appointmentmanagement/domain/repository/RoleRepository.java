package pl.beaution.appointmentmanagement.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.beaution.appointmentmanagement.domain.model.security.Role;

import java.util.Optional;
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(String roleName);
}
