package com.micronautlearning.integrationTest.controller;

import com.micronautlearning.user.jwtToken.JwtResponseDTO;
import com.micronautlearning.user.jwtToken.LoginRequest;
import com.micronautlearning.user.model.UserModel;
import com.micronautlearning.user.model.UserResponseDTO;
import com.micronautlearning.user.services.UserService;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import io.micronaut.http.client.annotation.Client;

@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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

    String jwt;

    @BeforeAll
    void authController_to_get_jwtToken(){
        var authData = new LoginRequest("john","password");
        HttpResponse<JwtResponseDTO> response = client.toBlocking().exchange(HttpRequest.POST("/auth/login",authData), JwtResponseDTO.class);
        JwtResponseDTO loginResponse = response.body();
        assertNotNull(loginResponse.getAccess_token());
        jwt = loginResponse.getAccess_token();
    }

    @Test
    void test_userController_to_create_user(){
        var userData = new UserModel("Hello","World","my@test3.com", UUID.randomUUID().toString(),null);
        when(userService.createUser(any(UserModel.class))).thenReturn(userData);
        HttpResponse<UserResponseDTO> response = client.toBlocking().exchange(HttpRequest.POST("/users",userData).bearerAuth(jwt), UserResponseDTO.class);
        UserResponseDTO myBody = response.getBody()
          .orElseThrow(() -> new RuntimeException("Response body is empty"));
        assertEquals("Hello",myBody.getFirstName());
        assertEquals("World",myBody.getLastName());
        assertEquals("my@test3.com",myBody.getEmail());
        assertNotNull(myBody.getUid());
    }
    @Test
    void test_userController_firstName_blank(){
        var userData = new UserModel("","World","my@test.com", UUID.randomUUID().toString(),null);
        HttpClientResponseException exception = assertThrows(HttpClientResponseException.class, () -> {
            client.toBlocking().exchange(HttpRequest.POST("/users", userData).header("Authorization", "Bearer " + jwt),UserModel.class);
        });
        assertEquals(HttpStatus.BAD_REQUEST,exception.getStatus());
    }

}