package br.com.orbitall.channels.canonicals;

import br.com.orbitall.channels.models.Customer;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionOutput(
    UUID id,
    BigDecimal amount,
    String cardType,
    LocalDateTime createdAt,
    boolean active,
    Customer customer
) {
}
