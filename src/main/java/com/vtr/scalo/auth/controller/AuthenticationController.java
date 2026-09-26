package com.vtr.scalo.auth.controller;

import com.vtr.scalo.auth.DTO.AuthenticationDTO;
import com.vtr.scalo.auth.DTO.LoginResponseDTO;
import com.vtr.scalo.auth.DTO.RegisterDTO;
import com.vtr.scalo.company.entity.Company;
import com.vtr.scalo.company.repository.CompanyRepository;
import com.vtr.scalo.security.TokenService;
import com.vtr.scalo.users.entity.User;
import com.vtr.scalo.users.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password4j.BcryptPassword4jPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final TokenService tokenService;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity registerAdmin(@RequestBody @Valid RegisterDTO data){
        if(this.userRepository.findByemail(data.login()) != null) return ResponseEntity.badRequest().build();
        Company company = companyRepository.findById(data.companyId()).orElseThrow(() -> new RuntimeException("Company not Found"));
        String encryptedPassword = new BcryptPassword4jPasswordEncoder().encode(data.password());
        User newUser = User.builder()
                .name(data.name())
                .email(data.login())
                .password(encryptedPassword)
                .role(data.role())
                .company(company)
                .build();
        this.userRepository.save(newUser);
        return ResponseEntity.ok().build();
    }
}
