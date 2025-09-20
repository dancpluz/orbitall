package br.com.orbitall.channels.canonicals;

import java.time.LocalDateTime;

public record CustomerOutput(
        String fullName,
        String email,
        String phone,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        boolean active
    ) {
}
