package com.vtr.scalo.company.controller;

import com.vtr.scalo.company.dto.CompanyRequestDTO;
import com.vtr.scalo.company.dto.CompanyResponseDTO;
import com.vtr.scalo.company.service.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CompanyControllerTest {

    @Mock
    private CompanyService companyService;

    @InjectMocks
    private CompanyController companyController;

    private MockMvc mockMvc;

    private CompanyRequestDTO request;
    private CompanyResponseDTO response;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(companyController)
                .build();
    }

    @BeforeEach
    void setupEntities() {
        request = CompanyRequestDTO.builder()
                .razaoSocial("Empresa LTDA")
                .nomeFantasia("Empresa")
                .cnpj("12345678000100")
                .email("empresa@email.com")
                .telefone("12999999999")
                .ownerId(UUID.randomUUID())
                .build();

        response = CompanyResponseDTO.builder()
                .razaoSocial("Empresa LTDA")
                .nomeFantasia("Empresa")
                .cnpj("12345678000100")
                .email("empresa@email.com")
                .telefone("12999999999")
                .ownerId(request.ownerId())
                .build();
    }

    @Test
    void shouldCreateCompany() throws Exception{

        //when
        when(companyService.create(any(CompanyRequestDTO.class)))
                .thenReturn(response);

        // then
        mockMvc.perform(
                        post("/api/v1/companies")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                            {
                                "razaoSocial": "Empresa LTDA",
                                "nomeFantasia": "Empresa",
                                "cnpj": "12345678000100",
                                "email": "empresa@email.com",
                                "telefone": "12999999999",
                                "ownerId": "%s"
                            }
                            """.formatted(request.ownerId()))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.razaoSocial").value("Empresa LTDA"))
                .andExpect(jsonPath("$.nomeFantasia").value("Empresa"))
                .andExpect(jsonPath("$.cnpj").value("12345678000100"))
                .andExpect(jsonPath("$.email").value("empresa@email.com"))
                .andExpect(jsonPath("$.telefone").value("12999999999"));
    }
}
