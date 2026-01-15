package com.sinvaldev.agregadordeinvestimentos.dtos;

import java.time.Instant;
import java.util.UUID;

public record ResponseUserDto(UUID userId, String userName, String email , Instant creationTimestamp, Instant updateTimestamp) {
}
