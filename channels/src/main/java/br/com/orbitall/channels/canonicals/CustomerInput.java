package br.com.orbitall.channels.canonicals;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CustomerInput(
        @NotBlank(message = "Full name cannot be null or empty")
        @Size(min = 1, max = 255, message = "Full name must be between 1 and 255 characters")
        String fullName,
        @NotBlank(message = "Email cannot be null or empty")
        @Size(min = 1, max = 100, message = "Email must be between 1 and 100 characters")
        String email,
        @NotBlank(message = "Phone cannot be null or empty")
        String phone
    ) {
}