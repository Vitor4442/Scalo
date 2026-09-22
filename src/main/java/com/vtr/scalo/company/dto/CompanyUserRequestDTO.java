package com.vtr.scalo.company.dto;

import com.vtr.scalo.users.dto.UserRequestDto;

public record CompanyUserRequestDTO(CompanyRequestDTO company, UserRequestDto user) {
}
