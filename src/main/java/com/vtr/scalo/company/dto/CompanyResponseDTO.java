package com.vtr.scalo.company.dto;

import com.vtr.scalo.company.entity.CompanyStatus;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record CompanyResponseDTO(
        UUID id,
        String razaoSocial,
        String nomeFantasia,
        String cnpj,
        String email,
        String telefone,
        CompanyStatus status,
        UUID ownerId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
){
}
