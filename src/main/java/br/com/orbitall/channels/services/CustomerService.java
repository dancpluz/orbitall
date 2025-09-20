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

    }

    public CustomerOutput retrieve(UUID id) {

    }

    public CustomerOutput update(UUID id, CustomerInput input) {

    }

    public CustomerOutput delete(UUID id) {

    }

    public List<CustomerOutput> findAll() {

    }

}

