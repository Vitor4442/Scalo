package com.vtr.scalo.users.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record UserResponseDTO(
        UUID id,
        String name,
        String email,
        Boolean active,
        UUID companyId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
){
}
