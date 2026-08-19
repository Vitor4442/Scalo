package com.vtr.scalo.users.mapper;

import com.vtr.scalo.company.entity.Company;
import com.vtr.scalo.users.dto.UserRequestDto;
import com.vtr.scalo.users.dto.UserResponseDTO;
import com.vtr.scalo.users.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Testes do UserMapper")
class UserMapperTest {

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

    @Nested
    @DisplayName("Testes para toEntity")
    class ToEntityTests {

        @Test
        @DisplayName("Deve converter UserRequestDTO e Company para User Entity")
        void shouldMapUserRequestDTOToUserEntity() {
            // Arrange
            UUID companyId = UUID.randomUUID();
            Company company = Company.builder().id(companyId).build();

            UserRequestDto dto = new UserRequestDto(
                    "Maria Souza",
                    "maria@email.com",
                    "senhaPlana123",
                    companyId
            );

            // Act
            User entity = userMapper.toEntity(dto, company);

            // Assert
            assertThat(entity).isNotNull();
            assertThat(entity.getName()).isEqualTo("Maria Souza");
            assertThat(entity.getEmail()).isEqualTo("maria@email.com");
            assertThat(entity.getPassword()).isEqualTo("senhaPlana123");
            assertThat(entity.getCompany()).isEqualTo(company);
            assertThat(entity.getCompany().getId()).isEqualTo(companyId);
        }

        @Test
        @DisplayName("Deve retornar null quando o DTO for nulo")
        void shouldReturnNullWhenDTOIsNull() {
            Company company = Company.builder().id(UUID.randomUUID()).build();
            User entity = userMapper.toEntity(null, company);
            assertThat(entity).isNull();
        }
    }

    @Nested
    @DisplayName("Testes para updateEntityFromDTO")
    class UpdateEntityTests {

        @Test
        @DisplayName("Deve atualizar os campos da entidade existente com os dados do DTO")
        void shouldUpdateEntityFromDTO() {
            // Arrange
            Company initialCompany = Company.builder().id(UUID.randomUUID()).build();
            User existingUser = User.builder()
                    .id(UUID.randomUUID())
                    .name("Nome Antigo")
                    .email("antigo@email.com")
                    .password("senhaAntiga")
                    .company(initialCompany)
                    .build();

            Company newCompany = Company.builder().id(UUID.randomUUID()).build();
            UserRequestDto updateDto = new UserRequestDto(
                    "Nome Novo",
                    "novo@email.com",
                    "novaSenha123",
                    newCompany.getId()
            );

            // Act
            userMapper.updateEntityFromDTO(updateDto, existingUser, newCompany);

            // Assert
            assertThat(existingUser.getName()).isEqualTo("Nome Novo");
            assertThat(existingUser.getEmail()).isEqualTo("novo@email.com");
            assertThat(existingUser.getPassword()).isEqualTo("novaSenha123");
            assertThat(existingUser.getCompany()).isEqualTo(newCompany);
        }

        @Test
        @DisplayName("Não deve alterar a senha se o DTO vier com senha em branco")
        void shouldNotUpdatePasswordIfBlankInDTO() {
            // Arrange
            User existingUser = User.builder()
                    .name("Nome")
                    .email("email@email.com")
                    .password("senhaAntigaMantida")
                    .build();

            UserRequestDto updateDto = new UserRequestDto(
                    "Novo Nome",
                    "email@email.com",
                    "   ", // Senha em branco
                    UUID.randomUUID()
            );

            // Act
            userMapper.updateEntityFromDTO(updateDto, existingUser, null);

            // Assert
            assertThat(existingUser.getName()).isEqualTo("Novo Nome");
            assertThat(existingUser.getPassword()).isEqualTo("senhaAntigaMantida");
        }
    }
}