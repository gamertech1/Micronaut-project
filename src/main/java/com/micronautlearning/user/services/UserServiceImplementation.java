package com.micronautlearning.user.services;

import com.micronautlearning.user.data.NewUserRepository;
import com.micronautlearning.user.data.UserRepository;
import com.micronautlearning.user.model.NewUserModel;
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
    private final NewUserRepository newUserRepository;

    public UserServiceImplementation(UserRepository userRepository, NewUserRepository newUserRepository) {
        this.userRepository = userRepository;
        this.newUserRepository = newUserRepository;
    }

    public UserModel createUser(UserModel userModel) {
        if (userModel.getUid() == null) {
            userModel.setUid(UUID.randomUUID().toString());
        }

        return userRepository.save(userModel); // 🚀 This saves to the database
    }
    public UserModel updateUser(String id, UpdateUserDetail request) {
        UserModel user = userRepository.findByUid(id).orElseThrow(() -> new HttpStatusException(HttpStatus.BAD_REQUEST, "User with ID " + id + " not found"));;
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        return userRepository.update(user); // 🚀 This update to the database
    }
    public UserModel getUser(String id) {
        return userRepository.findByUid(id)
                .orElseThrow(() -> new HttpStatusException(HttpStatus.BAD_REQUEST, "User with ID " + id + " not found"));
    }
    public UserModel deleteUser(String id) {
        UserModel user = userRepository.findByUid(id)
                .orElseThrow(() -> new HttpStatusException(HttpStatus.BAD_REQUEST, "User with ID " + id + " not found"));

        userRepository.deleteByUid(id);

        return user;
    }
    public NewUserModel createNewUser(NewUserModel userModel) {
         if (!userModel.getPassword().equals(userModel.getRepeatPassword())){
             throw new HttpStatusException(HttpStatus.BAD_REQUEST, "Password should match repeatPassword");
         }
        return newUserRepository.save(userModel);
    }
}


