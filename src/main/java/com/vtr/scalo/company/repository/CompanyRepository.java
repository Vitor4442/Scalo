package com.vtr.scalo.company.repository;

import com.vtr.scalo.company.entity.Company;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID> {

    boolean existsByCnpj(@NotBlank(message = "O CNPJ é obrigatório") @Pattern(regexp = "\\d{14}", message = "O CNPJ deve conter exatamente 14 dígitos numéricos") String cnpj);

    boolean existsByEmail(@NotBlank(message = "O e-mail é obrigatório") @Email(message = "E-mail inválido") @Size(max = 255, message = "O e-mail deve ter no máximo 255 caracteres") String email);
}
