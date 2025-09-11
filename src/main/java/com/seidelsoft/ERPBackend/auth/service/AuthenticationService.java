package com.seidelsoft.ERPBackend.auth.service;

import com.seidelsoft.ERPBackend.auth.model.dto.AuthenticationRequestDTO;
import com.seidelsoft.ERPBackend.auth.model.dto.AuthenticationResponseDTO;
import com.seidelsoft.ERPBackend.auth.model.dto.RegisterRequestDTO;
import com.seidelsoft.ERPBackend.auth.model.entity.User;
import com.seidelsoft.ERPBackend.auth.model.enumerations.RoleEnum;
import com.seidelsoft.ERPBackend.auth.repository.RoleRepository;
import com.seidelsoft.ERPBackend.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthenticationResponseDTO register(RegisterRequestDTO request) {
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(roleRepository.findById(RoleEnum.USER.getValue()).get()))
                .build();

        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponseDTO.builder().token(jwtToken).build();
    }

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword())
        );

        var user = userRepository.findByEmailWithPermissions(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponseDTO.builder().token(jwtToken).build();
    }

}
