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
    private UUID customerId;
}
