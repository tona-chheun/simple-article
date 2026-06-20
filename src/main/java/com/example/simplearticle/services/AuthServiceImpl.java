package com.example.simplearticle.services;

import com.example.simplearticle.mappers.UserMapper;
import com.example.simplearticle.models.User;
import com.example.simplearticle.repositories.UserRepository;
import com.example.simplearticle.requests.AuthRequest;
import com.example.simplearticle.requests.UserRequest;
import com.example.simplearticle.response.AuthResponse;
import com.example.simplearticle.response.UserResponse;
import com.example.simplearticle.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    public AuthServiceImpl(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            UserMapper userMapper
    ) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponse register(UserRequest payload) {
        User user = this.userRepository.save(this.userMapper.toEntity(payload));
        return this.userMapper.toResponse(user);
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        User user = userRepository.findByEmailAndDeletedAtIsNull(request.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtService.generateToken(
                org.springframework.security.core.userdetails.User
                        .withUsername(user.getEmail())
                        .password(user.getPassword())
                        .authorities(List.of())
                        .build()
        );

        return new AuthResponse(token);
    }
}
