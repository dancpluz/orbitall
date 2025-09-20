package br.com.orbitall.channels.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "TRANSACTIONS")
@Data
public class Transaction {
    @Id private UUID id;
    private BigDecimal amount;
    private String cardType;
    private LocalDateTime createdAt;
    private boolean active;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false) // Defines the foreign key column in this table.
    private Customer customer;
}
