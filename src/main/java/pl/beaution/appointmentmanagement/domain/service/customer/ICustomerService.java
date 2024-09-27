package pl.beaution.appointmentmanagement.domain.service.customer;

import pl.beaution.appointmentmanagement.domain.model.Customer;

import java.util.List;

public interface ICustomerService {
    Customer createCustomer(Customer customer);

    Customer updateCustomer(Customer customer) throws IllegalAccessException;

    List<Customer> getAllCustomers();

    Customer getCustomerById(Long id);

    void deleteCustomer(Long id);
}
