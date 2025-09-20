package br.com.orbitall.channels.canonicals;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionInput(
    @NotNull(message = "Customer Id cannot be null")
    UUID customerId,
    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be a positive value")
    BigDecimal amount,
    @NotBlank(message = "Card type cannot be blank")
    @Pattern(regexp = "^(CRÉDITO|DÉBITO)$", message = "Card type must be 'CRÉDITO' or 'DÉBITO'")
    String cardType
){
}
