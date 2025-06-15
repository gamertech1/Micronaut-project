package com.micronautlearning.user.data;
import com.micronautlearning.user.model.UserModel;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;

import java.util.UUID;

@Singleton
public class DataSeeder {

    private final UserRepository userRepository;

    public DataSeeder(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    void init() {
        userRepository.save(new UserModel("John", "Doe", "john@example.com",UUID.randomUUID().toString(),1));
    }
}

