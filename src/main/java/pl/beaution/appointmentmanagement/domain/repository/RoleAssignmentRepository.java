package pl.beaution.appointmentmanagement.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.beaution.appointmentmanagement.domain.model.security.RoleAssignment;

import java.util.List;
import java.util.Optional;

public interface RoleAssignmentRepository extends JpaRepository<RoleAssignment, Long> {
    List<RoleAssignment> findByUserId(Long userId);
}
