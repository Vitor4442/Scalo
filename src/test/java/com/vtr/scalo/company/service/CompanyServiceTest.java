package com.vtr.scalo.company.service;

import com.vtr.scalo.company.dto.CompanyRequestDTO;
import com.vtr.scalo.company.dto.CompanyResponseDTO;
import com.vtr.scalo.company.entity.Company;
import com.vtr.scalo.company.exceptions.CompanyDuplicate;
import com.vtr.scalo.company.mapper.CompanyMapper;
import com.vtr.scalo.company.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private CompanyMapper companyMapper;

    @InjectMocks
    private CompanyService companyService;

    private Company company;
    private CompanyRequestDTO requestDTO;
    private CompanyResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        company = new Company();

        requestDTO = CompanyRequestDTO.builder()
                .razaoSocial("Empresa LTDA")
                .nomeFantasia("Empresa")
                .cnpj("12345678000100")
                .email("empresa@email.com")
                .telefone("12999999999")
                .ownerId(UUID.randomUUID())
                .build();

        responseDTO = CompanyResponseDTO.builder()
                .razaoSocial("Empresa LTDA")
                .nomeFantasia("Empresa")
                .cnpj("12345678000100")
                .email("empresa@email.com")
                .telefone("12999999999")
                .ownerId(requestDTO.ownerId())
                .build();
    }

    @Test
    void shouldCreateCompanySuccessfully() {
        when(companyRepository.existsByCnpj(requestDTO.cnpj()))
                .thenReturn(false);

        when(companyRepository.existsByEmail(requestDTO.email()))
                .thenReturn(false);

        when(companyMapper.toEntity(requestDTO))
                .thenReturn(company);

        when(companyRepository.save(company))
                .thenReturn(company);

        when(companyMapper.toDTO(company))
                .thenReturn(responseDTO);

        CompanyResponseDTO result = companyService.create(requestDTO);

        assertNotNull(result);
        assertEquals(responseDTO, result);

        verify(companyRepository).existsByCnpj(requestDTO.cnpj());
        verify(companyRepository).existsByEmail(requestDTO.email());
        verify(companyMapper).toEntity(requestDTO);
        verify(companyRepository).save(company);
        verify(companyMapper).toDTO(company);
    }

    @Test
    void shouldThrowExceptionWhenCnpjAlreadyExists() {
        when(companyRepository.existsByCnpj(requestDTO.cnpj()))
                .thenReturn(true);

        CompanyDuplicate exception = assertThrows(
                CompanyDuplicate.class,
                () -> companyService.create(requestDTO)
        );

        assertEquals(
                "Já existe uma empresa cadastrada com o CNPJ informado.",
                exception.getMessage()
        );

        verify(companyRepository).existsByCnpj(requestDTO.cnpj());

        verify(companyRepository, never())
                .existsByEmail(any());

        verify(companyRepository, never())
                .save(any());

        verify(companyMapper, never())
                .toEntity(any());
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        when(companyRepository.existsByCnpj(requestDTO.cnpj()))
                .thenReturn(false);

        when(companyRepository.existsByEmail(requestDTO.email()))
                .thenReturn(true);

        CompanyDuplicate exception = assertThrows(
                CompanyDuplicate.class,
                () -> companyService.create(requestDTO)
        );

        assertEquals(
                "Já existe uma empresa cadastrada com o e-mail informado.",
                exception.getMessage()
        );

        verify(companyRepository).existsByCnpj(requestDTO.cnpj());
        verify(companyRepository).existsByEmail(requestDTO.email());

        verify(companyRepository, never())
                .save(any());

        verify(companyMapper, never())
                .toEntity(any());
    }

    @Test
    void shouldFindCompanyByIdSuccessfully() {
        UUID id = UUID.randomUUID();

        when(companyRepository.findById(id))
                .thenReturn(Optional.of(company));

        when(companyMapper.toDTO(company))
                .thenReturn(responseDTO);

        CompanyResponseDTO result = companyService.findById(id);

        assertNotNull(result);
        assertEquals(responseDTO, result);

        verify(companyRepository).findById(id);
        verify(companyMapper).toDTO(company);
    }
}