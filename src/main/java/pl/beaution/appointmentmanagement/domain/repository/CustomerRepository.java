package pl.beaution.appointmentmanagement.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.beaution.appointmentmanagement.domain.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
