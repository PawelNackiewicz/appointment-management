package pl.beaution.appointmentmanagement.domain.service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.beaution.appointmentmanagement.domain.model.Customer;
import pl.beaution.appointmentmanagement.domain.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements ICustomerService{
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        try {
            return customerRepository.save(customer);
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not create customer");
        }
    }

    @Override
    public Customer updateCustomer(Customer customer) throws IllegalAccessException {
        Optional<Customer> existingCustomer = customerRepository.findById(customer.getId());
        if(existingCustomer.isEmpty()) {
            throw new IllegalAccessException("Customer with id " + customer.getId() + " does not exist");
        }
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer with ID " + id + " not found."));
    }
    @Override
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new IllegalArgumentException("Customer with ID " + id + " does not exist.");
        }
        customerRepository.deleteById(id);
    }
}
