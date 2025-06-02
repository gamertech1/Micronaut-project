package com.micronautlearning.user.services;

import com.micronautlearning.user.model.User;

public interface UserService {

    User createUser(String name,
                    String lastName,
                    String email,
                    String userId);

}

