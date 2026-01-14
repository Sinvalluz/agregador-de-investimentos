package com.sinvaldev.agregadordeinvestimentos.dtos;

import java.time.Instant;
import java.util.UUID;

public record UserDto(UUID userId, String userName, String email, String password, Instant creationTimestamp, Instant updateTimestamp) {
}
