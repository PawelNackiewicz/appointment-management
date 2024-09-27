package pl.beaution.appointmentmanagement.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.beaution.appointmentmanagement.domain.model.Salon;

public interface SalonRepository extends JpaRepository<Salon, Long> {
}
