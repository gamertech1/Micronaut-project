package com.micronautlearning.integrationTest.controller;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.Test;

import com.micronautlearning.user.model.UserModel;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;

@MicronautTest
public class UserControllerValidationTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void test_userController_firstName_blank() {
        UserModel userData = new UserModel("", "", "my", UUID.randomUUID().toString());

        HttpClientResponseException exception = assertThrows(HttpClientResponseException.class, () -> {
            client.toBlocking().retrieve(
                    HttpRequest.POST("/users", userData)
                            .contentType(MediaType.APPLICATION_JSON)
            );
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }
}
