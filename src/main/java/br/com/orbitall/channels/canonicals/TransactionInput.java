package br.com.orbitall.channels.canonicals;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionInput(
        @NotBlank(message = "Customer Id cannot be null or empty")
        UUID customerId,
        @NotBlank(message = "Amount cannot be null or empty")
        BigDecimal amount,
        @NotBlank(message = "Card type needs to be 'DÉBITO' or 'CRÉDITO'")
        String cardType
) {
}
