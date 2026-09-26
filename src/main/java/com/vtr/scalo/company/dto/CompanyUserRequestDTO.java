package com.vtr.scalo.company.dto;

import com.vtr.scalo.users.dto.UserRequestDto;
import jakarta.validation.Valid;

public record CompanyUserRequestDTO(
        @Valid
        CompanyRequestDTO company,
        @Valid
        UserRequestDto user) {
}
