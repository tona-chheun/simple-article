package com.example.simplearticle.mappers;

import com.example.simplearticle.models.User;
import com.example.simplearticle.requests.UserRequest;
import com.example.simplearticle.requests.UserUpdateRequest;
import com.example.simplearticle.response.UserResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements DataMapper<User, UserRequest, UserResponse>{
    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User toEntity(UserRequest payload) {
        User user = new User();
        user.setName(payload.name());
        user.setEmail(payload.email());
        user.setPassword(passwordEncoder.encode(payload.password()));
        return user;
    }

    public void updateEntity(User user, UserUpdateRequest request) {
        user.setName(request.name());
        user.setEmail(request.email());
    }

    @Override
    public UserResponse toResponse(User entity) {
        return new UserResponse(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
