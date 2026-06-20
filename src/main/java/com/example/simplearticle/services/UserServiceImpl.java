package com.example.simplearticle.services;

import com.example.simplearticle.exceptions.RecordNotFoundException;
import com.example.simplearticle.mappers.UserMapper;
import com.example.simplearticle.models.User;
import com.example.simplearticle.repositories.UserRepository;
import com.example.simplearticle.requests.UserRequest;
import com.example.simplearticle.requests.UserUpdateRequest;
import com.example.simplearticle.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(
            UserRepository userRepository,
            UserMapper userMapper
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        User user = userRepository.findByEmailAndDeletedAtIsNull(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(List.of())
                .build();
    }

    @Override
    public List<UserResponse> getAll() {
        return this.userRepository.findByDeletedAtIsNull()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public UserResponse findById(UUID id) {
        User user = userRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(() -> new RecordNotFoundException(id));
        return this.userMapper.toResponse(user);
    }

    @Override
    public UserResponse create(UserRequest payload) {
        User user = this.userRepository.save(this.userMapper.toEntity(payload));
        return this.userMapper.toResponse(user);
    }

    @Override
    public UserResponse update(UUID id, UserUpdateRequest payload) {
        User user = userRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(() -> new RecordNotFoundException(id));
        this.userMapper.updateEntity(user, payload);
        return this.userMapper.toResponse(this.userRepository.save(user));
    }

    @Override
    public void delete(UUID id) {
        User user = userRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(() -> new RecordNotFoundException(id));
        user.setDeletedAt(LocalDateTime.now());
        this.userRepository.save(user);
    }
}
