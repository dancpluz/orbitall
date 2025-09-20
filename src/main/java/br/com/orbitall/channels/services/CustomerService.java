package br.com.orbitall.channels.services;

import br.com.orbitall.channels.canonicals.CustomerInput;
import br.com.orbitall.channels.canonicals.CustomerOutput;
import br.com.orbitall.channels.models.Customer;
import br.com.orbitall.channels.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository repository;

    public CustomerOutput create(CustomerInput input) {
        LocalDateTime now = LocalDateTime.now();

        Customer customer = new Customer();

        customer.setId(UUID.randomUUID());
        customer.setFullName(input.fullName());
        customer.setEmail(input.email());
        customer.setPhone(input.phone());
        customer.setCreatedAt(now);
        customer.setUpdatedAt(now);
        customer.setActive(true);

        repository.save(customer);

        return new CustomerOutput(
                customer.getId(),
                customer.getFullName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getCreatedAt(),
                customer.getUpdatedAt(),
                customer.isActive()
        );
    }

    public CustomerOutput retrieve(UUID id) {
        Customer customer = repository
                .findById(id)
                .filter(Customer::isActive)
                .orElseThrow(() -> new ResourceNotFoundException("Not found the resource (id: " + id + ")"));

        return new CustomerOutput(
                customer.getId(),
                customer.getFullName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getCreatedAt(),
                customer.getUpdatedAt(),
                customer.isActive()
        );
    }

    public CustomerOutput update(UUID id, CustomerInput input) {

    }

    public CustomerOutput delete(UUID id) {

    }

    public List<CustomerOutput> findAll() {

    }

}

