package com.vtr.scalo.users.mapper;

import com.vtr.scalo.company.entity.Company;
import com.vtr.scalo.users.dto.UserRequestDto;
import com.vtr.scalo.users.dto.UserResponseDTO;
import com.vtr.scalo.users.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDTO toDTO(User entity) {
        if (entity == null) {
            return null;
        }

        return UserResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .active(entity.getActive())
                .companyId(entity.getCompany() != null ? entity.getCompany().getId() : null)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public User toEntity(UserRequestDto dto, Company company) {
        if (dto == null) {
            return null;
        }

        return User.builder()
                .name(dto.name())
                .email(dto.email())
                .password(dto.password())
                .company(company)
                .build();
    }

    public void updateEntityFromDTO(UserRequestDto dto, User entity, Company newCompany) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setName(dto.name());
        entity.setEmail(dto.email());
        if (dto.password() != null && !dto.password().isBlank()) {
            entity.setPassword(dto.password());
        }
        if (newCompany != null) {
            entity.setCompany(newCompany);
        }
    }
}