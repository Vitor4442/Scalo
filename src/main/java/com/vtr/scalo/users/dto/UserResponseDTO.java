package com.vtr.scalo.users.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record UserResponseDTO(
        Integer id,
        String name,
        String email,
        Boolean active,
        Integer companyId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
){
}
