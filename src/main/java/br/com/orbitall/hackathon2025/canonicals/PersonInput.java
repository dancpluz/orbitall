package br.com.orbitall.hackathon2025.canonicals;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PersonInput(
        @NotBlank(message = "Full name cannot be null or empty")
        @Size(min = 1, max = 255, message = "Full name must be between 1 and 255 characters")
        String fullName,
        @Min(value = 1, message = "Age must be between 1 and 120")
        @Max(value = 120, message = "Age must be between 1 and 120")
        int age,
        @Size(max = 255, message = "Description must be between 0 and 255 characters")
        String description
) {
}
