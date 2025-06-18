package com.micronautlearning.integrationTest.controller;

import com.micronautlearning.user.data.UserRepository;
import com.micronautlearning.user.model.UserModel;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;

@MicronautTest()
public class UserModelEntityTest {
    @Inject
    UserRepository userRepository;

    @Test
    void test_user_creation_and_saving(){
        String uid = UUID.randomUUID().toString() ;
        UserModel userModel = new UserModel("Johnchan","Cena","Johncena129@gmail.com",uid ,null);
        userRepository.save(userModel);
        Optional<UserModel> found = userRepository.findByUid(uid);
        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals("Johnchan", found.get().getFirstName());
        Assertions.assertEquals("Cena", found.get().getLastName());
        Assertions.assertEquals("Johncena129@gmail.com", found.get().getEmail());
        Assertions.assertEquals(uid, found.get().getUid());


    }
}
