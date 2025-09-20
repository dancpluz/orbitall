package br.com.orbitall.channels.services;

import br.com.orbitall.channels.canonicals.TransactionInput;
import br.com.orbitall.channels.canonicals.TransactionOutput;
import br.com.orbitall.channels.exceptions.ResourceNotFoundException;
import br.com.orbitall.channels.models.Customer;
import br.com.orbitall.channels.models.Transaction;
import br.com.orbitall.channels.repositories.CustomerRepository;
import br.com.orbitall.channels.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private TransactionOutput toOutput(Transaction transaction, Customer customer) {
        return new TransactionOutput(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getCardType(),
                transaction.getCreatedAt(),
                transaction.isActive(),
                customer
        );
    }

    public TransactionOutput create(TransactionInput input) {
        Customer customer = customerRepository
                .findById(input.customerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + input.customerId()));

        Transaction transaction = new Transaction();

        transaction.setId(UUID.randomUUID());
        transaction.setAmount(input.amount());
        transaction.setCardType(input.cardType());
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setActive(true);

        transaction.setCustomerId(customer.getId());

        Transaction savedTransaction = transactionRepository.save(transaction);
        return toOutput(savedTransaction, customer);
    }

    public List<TransactionOutput> retrieve(UUID customerId) {
        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));

        List<TransactionOutput> outputs = new ArrayList<>();
        transactionRepository.findAllByCustomerId(customer.getId()).forEach(tx -> {
            TransactionOutput out = toOutput(tx, customer);
            outputs.add(out);
        });

        return outputs;
    }
}
