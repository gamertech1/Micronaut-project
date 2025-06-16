package com.micronautlearning.user.jwtToken;

import com.micronautlearning.user.data.NewUserRepository;
import jakarta.inject.Singleton;

@Singleton
public class AuthService {

    private final NewUserRepository userRepository;

    public AuthService(NewUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean authenticate(LoginRequest loginRequest) {
        return userRepository.findByUserName(loginRequest.username())
                .map(user -> user.getPassword().equals(loginRequest.password())) // You should hash and compare!
                .orElse(false);
    }
}


