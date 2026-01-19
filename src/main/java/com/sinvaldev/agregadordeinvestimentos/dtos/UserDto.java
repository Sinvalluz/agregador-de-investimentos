package com.sinvaldev.agregadordeinvestimentos.dtos;

import java.time.Instant;
import java.util.UUID;

public record UserDto(UUID userId, String userName, String email, Instant creationTimestamp, Instant updateTimestamp) {
}
