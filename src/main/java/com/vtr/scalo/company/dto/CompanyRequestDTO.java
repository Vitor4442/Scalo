package com.vtr.scalo.company.dto;

import com.vtr.scalo.company.entity.CompanyStatus;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.util.UUID;

@Builder
public record CompanyRequestDTO(
        @NotBlank(message = "A razão social é obrigatória")
        @Size(max = 255, message = "A razão social deve ter no máximo 255 caracteres")
        String razaoSocial,

        @NotBlank(message = "O nome fantasia é obrigatório")
        @Size(max = 255, message = "O nome fantasia deve ter no máximo 255 caracteres")
        String nomeFantasia,

        @NotBlank(message = "O CNPJ é obrigatório")
        @Pattern(regexp = "\\d{14}", message = "O CNPJ deve conter exatamente 14 dígitos numéricos")
        String cnpj,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "E-mail inválido")
        @Size(max = 255, message = "O e-mail deve ter no máximo 255 caracteres")
        String email,

        @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres")
        String telefone,

        CompanyStatus status,

        @NotNull(message = "O ID do dono é obrigatório")
        UUID ownerId
) {}
