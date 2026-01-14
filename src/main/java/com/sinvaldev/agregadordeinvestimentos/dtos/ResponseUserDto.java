package com.sinvaldev.agregadordeinvestimentos.dtos;

import java.time.Instant;
import java.util.UUID;

public record ResponseUserDto(UUID userId, String userName, String email, String password, Instant creationTimestamp, Instant updateTimestamp) {
}
