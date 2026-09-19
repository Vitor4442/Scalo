package com.vtr.scalo.company.dto;

import com.vtr.scalo.users.dto.UserResponseDTO;
import lombok.Builder;

@Builder
public record CompanyUserResponseDTO(CompanyResponseDTO company, UserResponseDTO user) {
}
