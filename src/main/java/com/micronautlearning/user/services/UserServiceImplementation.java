package com.micronautlearning.user.services;

import com.micronautlearning.user.data.UserRepository;
import com.micronautlearning.user.model.UpdateUserDetail;
import com.micronautlearning.user.model.UserModel;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.exceptions.HttpStatusException;
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
    public UserModel updateUser(String id, UpdateUserDetail request) {
        UserModel user = userRepository.findById(id).orElseThrow(() -> new HttpStatusException(HttpStatus.BAD_REQUEST, "User with ID " + id + " not found"));;
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        return userRepository.update(user); // 🚀 This update to the database
    }
}


