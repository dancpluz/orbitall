package br.com.orbitall.channels.repositories;

import br.com.orbitall.channels.models.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CustomerRepository extends CrudRepository<Customer, UUID> {}
