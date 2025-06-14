//package com.micronautlearning.user.data;
//
//import com.micronautlearning.user.model.UserModel;
//import io.micronaut.context.annotation.Primary;
//import jakarta.inject.Singleton;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Singleton
//@Primary
//public class UserRepositoryImpl implements UserRepository {
//    private final Map<String, UserModel> datastore = new ConcurrentHashMap<>();
//
//    @Override
//    public UserModel save(UserModel userModel) {
//        datastore.put(userModel.getId(), userModel);
//        return userModel;
//    }
//}
