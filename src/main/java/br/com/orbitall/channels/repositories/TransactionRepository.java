package br.com.orbitall.channels.repositories;

import br.com.orbitall.channels.models.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TransactionRepository extends CrudRepository<Transaction, UUID> {
    Iterable<Transaction> findAllByCustomerId(UUID customerId);
}
