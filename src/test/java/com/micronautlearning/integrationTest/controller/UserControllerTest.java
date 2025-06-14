package com.micronautlearning.integrationTest.controller;

import com.micronautlearning.user.model.UserModel;
import com.micronautlearning.user.services.UserService;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import io.micronaut.http.client.annotation.Client;
@MicronautTest
class UserControllerTest {


    @Inject
    UserService userService;

    @MockBean(UserService.class)
    UserService userService(){
      return mock(UserService.class);
    }
    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void test_userController_to_create_user(){
        var userData = new UserModel("Hello","World","my@test.com", UUID.randomUUID().toString());
        when(userService.createUser(any(UserModel.class))).thenReturn(userData);
        HttpResponse<UserModel> response = client.toBlocking().exchange(HttpRequest.POST("/users",userData), UserModel.class);
        UserModel myBody = response.getBody()
          .orElseThrow(() -> new RuntimeException("Response body is empty"));
        assertEquals("Hello",myBody.getFirstName());
        assertEquals("World",myBody.getLastName());
        assertEquals("my@test.com",myBody.getEmail());
        assertNotNull(myBody.getId());
    }
    @Test
    void test_userController_firstName_blank(){
        var userData = new UserModel("","World","my@test.com", UUID.randomUUID().toString());
        HttpClientResponseException exception = assertThrows(HttpClientResponseException.class, () -> {
            client.toBlocking().exchange(HttpRequest.POST("/users", userData), UserModel.class);
        });
        assertEquals(HttpStatus.BAD_REQUEST,exception.getStatus());
    }

}