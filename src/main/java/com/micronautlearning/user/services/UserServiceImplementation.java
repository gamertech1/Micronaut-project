package com.micronautlearning.user.services;

import com.micronautlearning.user.data.UserRepository;
import com.micronautlearning.user.model.User;
import jakarta.inject.Singleton;

import java.util.UUID;

@Singleton
public class UserServiceImplementation  implements UserService{


    UserRepository userRepository;

    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(String firstName,
                           String lastName,
                           String email,
                           String userId) {
        if(firstName== null || firstName.isEmpty()){
            throw new IllegalArgumentException("Users First Name should not be Empty");
        }
        User user = new User(firstName,lastName,email, UUID.randomUUID().toString());
        return user;
    }
}


