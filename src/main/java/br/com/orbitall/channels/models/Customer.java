package br.com.orbitall.channels.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "CUSTOMERS")
@Data
public class Customer {
    @Id private UUID id;
    private String fullName;
    private String email;
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean active;
    @OneToMany(
        mappedBy = "customer",
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private Set<Transaction> transactions = new HashSet<>();
}
