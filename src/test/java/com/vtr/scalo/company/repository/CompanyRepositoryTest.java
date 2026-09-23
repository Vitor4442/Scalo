package com.vtr.scalo.company.repository;

import com.vtr.scalo.company.entity.Company;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    void shouldReturnTrueWhenCnpjExists() {
        Company company = createCompany();

        companyRepository.save(company);

        boolean exists = companyRepository.existsByCnpj(company.getCnpj());

        assertTrue(exists);
    }

    @Test
    void shouldReturnFalseWhenCnpjDoesNotExist() {
        boolean exists =
                companyRepository.existsByCnpj("12345678000199");

        assertFalse(exists);
    }

    @Test
    void shouldReturnTrueWhenEmailExists() {
        Company company = createCompany();

        companyRepository.save(company);

        boolean exists = companyRepository.existsByEmail(company.getEmail());

        assertTrue(exists);
    }

    @Test
    void shouldReturnFalseWhenEmailDoesNotExist() {
        boolean exists =
                companyRepository.existsByEmail("naoexiste@email.com");

        assertFalse(exists);
    }

    private Company createCompany() {
        return Company.builder()
                .cnpj("12345678000100")
                .email("empresa@email.com")
                .build();
    }
}
