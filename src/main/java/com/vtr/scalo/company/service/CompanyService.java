package com.vtr.scalo.company.service;

import com.vtr.scalo.company.dto.CompanyRequestDTO;
import com.vtr.scalo.company.dto.CompanyResponseDTO;
import com.vtr.scalo.company.dto.CompanyUserRequestDTO;
import com.vtr.scalo.company.dto.CompanyUserResponseDTO;
import com.vtr.scalo.company.entity.Company;
import com.vtr.scalo.company.exceptions.CompanyDuplicate;
import com.vtr.scalo.company.exceptions.CompanyNotFoundException;
import com.vtr.scalo.company.mapper.CompanyMapper;
import com.vtr.scalo.company.repository.CompanyRepository;
import com.vtr.scalo.users.dto.UserRequestDto;
import com.vtr.scalo.users.dto.UserResponseDTO;
import com.vtr.scalo.users.entity.User;
import com.vtr.scalo.users.mapper.UserMapper;
import com.vtr.scalo.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final UserMapper userMapper;
    private final CompanyMapper companyMapper;
    private final UserRepository userRepository;

    @Transactional
    public CompanyUserResponseDTO create(CompanyUserRequestDTO request) {
        if (companyRepository.existsByCnpj(request.company().cnpj())) {
            throw new CompanyDuplicate("Já existe uma empresa cadastrada com o CNPJ informado.");
        }
        if (companyRepository.existsByEmail(request.company().email())) {
            throw new CompanyDuplicate("Já existe uma empresa cadastrada com o e-mail informado.");
        }
        if (userRepository.existsByEmail(request.user().email())){
            throw new CompanyDuplicate("Já existe uma empresa cadastrada com o e-mail informado.");
        }

        Company company = companyMapper.toEntity(request.company());
        User user = userMapper.toEntity(request.user(), company);

        CompanyResponseDTO savedCompany = companyMapper.toDTO(companyRepository.save(company));
        UserResponseDTO savedUser = userMapper.toDTO(userRepository.save(user));

        return CompanyUserResponseDTO.builder()
                .company(savedCompany)
                .user(savedUser)
                .build();
    }

    @Transactional(readOnly = true)
    public CompanyResponseDTO findById(Integer id) {
        return companyRepository.findById(id)
                .map(companyMapper::toDTO)
                .orElseThrow(() -> new CompanyNotFoundException("Empresa não encontrada com o ID: " + id));
    }

    @Transactional(readOnly = true)
    public Page<CompanyResponseDTO> findAll(Pageable pageable) {
        return companyRepository.findAll(pageable)
                .map(companyMapper::toDTO);
    }

    @Transactional
    public CompanyResponseDTO update(Integer id, CompanyRequestDTO dto) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException("Empresa não encontrada com o ID: " + id));

        companyMapper.updateEntityFromDTO(dto, company);
        Company updatedCompany = companyRepository.save(company);
        return companyMapper.toDTO(updatedCompany);
    }

    @Transactional
    public void delete(Integer id) {
        if (!companyRepository.existsById(id)) {
            throw new CompanyNotFoundException("Empresa não encontrada com o ID: " + id);
        }
        companyRepository.deleteById(id);
    }
}