package com.vtr.scalo.company.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "companies")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // Exigido pelo JPA
@AllArgsConstructor
@Builder
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "razao_social", nullable = false, length = 255)
    private String razaoSocial;

    @Column(name = "nome_fantasia", nullable = false, length = 255)
    private String nomeFantasia;

    @Column(nullable = false, unique = true, length = 14)
    private String cnpj;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(length = 20)
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private CompanyStatus status = CompanyStatus.ATIVA;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}