package com.micronautlearning.user.data;

import com.micronautlearning.user.model.User;
import jakarta.inject.Singleton;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class UserRepositoryImplementation implements UserRepository {


    Map<String, User> userMap = new HashMap<>();

    @Override
    public boolean save(User user) {
        boolean isValuePresent = false;
        if(!userMap.containsKey(user.getId())){
            userMap.put(user.getId(), user);
            isValuePresent= true;
        }
        return isValuePresent;
    }
}
