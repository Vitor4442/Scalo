package com.vtr.scalo.company.mapper;

import com.vtr.scalo.company.dto.CompanyRequestDTO;
import com.vtr.scalo.company.dto.CompanyResponseDTO;
import com.vtr.scalo.company.entity.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {

    public CompanyResponseDTO toDTO(Company entity) {
        if (entity == null) {
            return null;
        }

        return CompanyResponseDTO.builder()
                .id(entity.getId())
                .razaoSocial(entity.getRazaoSocial())
                .nomeFantasia(entity.getNomeFantasia())
                .cnpj(entity.getCnpj())
                .email(entity.getEmail())
                .telefone(entity.getTelefone())
                .status(entity.getStatus())
                .ownerId(entity.getOwnerId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public Company toEntity(CompanyRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        var builder = Company.builder()
                .razaoSocial(dto.razaoSocial())
                .nomeFantasia(dto.nomeFantasia())
                .cnpj(dto.cnpj())
                .email(dto.email())
                .telefone(dto.telefone())
                .ownerId(dto.ownerId());

        if (dto.status() != null) {
            builder.status(dto.status());
        }

        return builder.build();
    }

    public void updateEntityFromDTO(CompanyRequestDTO dto, Company entity) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setRazaoSocial(dto.razaoSocial());
        entity.setNomeFantasia(dto.nomeFantasia());
        entity.setCnpj(dto.cnpj());
        entity.setEmail(dto.email());
        entity.setTelefone(dto.telefone());
        entity.setOwnerId(dto.ownerId());

        if (dto.status() != null) {
            entity.setStatus(dto.status());
        }
    }
}