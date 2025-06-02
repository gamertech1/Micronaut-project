package com.micronautlearning.user.data;

import com.micronautlearning.user.model.User;

public interface UserRepository {
    boolean save(User user);
}
