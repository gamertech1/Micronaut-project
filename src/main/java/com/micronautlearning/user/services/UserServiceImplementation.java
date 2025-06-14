package com.micronautlearning.user.services;

import com.micronautlearning.user.data.UserRepository;
import com.micronautlearning.user.model.UserModel;

import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;

import java.util.UUID;

@Singleton
public class UserServiceImplementation  implements UserService{

    private final UserRepository userRepository;

    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel createUser(UserModel userModel) {
        if (userModel.getId() == null) {
            userModel.setId(UUID.randomUUID().toString());
        }

        return userRepository.save(userModel); // 🚀 This saves to the database
    }
}


