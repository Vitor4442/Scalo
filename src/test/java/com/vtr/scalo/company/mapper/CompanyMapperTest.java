package com.vtr.scalo.company.mapper;

import com.vtr.scalo.company.entity.Company;
import com.vtr.scalo.users.dto.UserResponseDTO;
import com.vtr.scalo.users.entity.User;
import com.vtr.scalo.users.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Testes do UserMapper")
public class CompanyMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();
    }

    @Nested
    @DisplayName("Testes para toDTO")
    class ToDTOTests {

        @Test
        @DisplayName("Deve converter User Entity completo para UserResponseDTO")
        void shouldMapUserEntityToUserResponseDTO() {
            // Arrange
            UUID userId = UUID.randomUUID();
            UUID companyId = UUID.randomUUID();
            LocalDateTime now = LocalDateTime.now();

            Company company = Company.builder()
                    .id(companyId)
                    .razaoSocial("Empresa Teste Ltda")
                    .build();

            User user = User.builder()
                    .id(userId)
                    .name("João Silva")
                    .email("joao.silva@email.com")
                    .password("senhaCriptografada123")
                    .active(true)
                    .company(company)
                    .createdAt(now)
                    .updatedAt(now)
                    .build();

            // Act
            UserResponseDTO dto = userMapper.toDTO(user);

            // Assert
            assertThat(dto).isNotNull();
            assertThat(dto.id()).isEqualTo(userId);
            assertThat(dto.name()).isEqualTo("João Silva");
            assertThat(dto.email()).isEqualTo("joao.silva@email.com");
            assertThat(dto.active()).isTrue();
            assertThat(dto.companyId()).isEqualTo(companyId);
            assertThat(dto.createdAt()).isEqualTo(now);
            assertThat(dto.updatedAt()).isEqualTo(now);
        }

        @Test
        @DisplayName("Deve converter User com Company nula mapeando companyId como null")
        void shouldMapUserEntityWithNullCompanyToDTO() {
            // Arrange
            User user = User.builder()
                    .id(UUID.randomUUID())
                    .name("User sem empresa")
                    .email("no.company@email.com")
                    .company(null)
                    .build();

            // Act
            UserResponseDTO dto = userMapper.toDTO(user);

            // Assert
            assertThat(dto).isNotNull();
            assertThat(dto.companyId()).isNull();
        }

        @Test
        @DisplayName("Deve retornar null quando passar entidade nula")
        void shouldReturnNullWhenEntityIsNull() {
            UserResponseDTO dto = userMapper.toDTO(null);
            assertThat(dto).isNull();
        }
    }
}
