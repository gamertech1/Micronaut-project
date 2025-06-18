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
import net.datafaker.Faker;
import org.junit.jupiter.api.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import io.micronaut.http.client.annotation.Client;

@MicronautTest()
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
    Faker faker = new Faker();

     private String getJwt() {
       var authData = new LoginRequest("JohnCena","12345678");
            return client.toBlocking()
            .exchange(HttpRequest.POST("/auth/login", authData), JwtResponseDTO.class)
            .body().getAccess_token();
}
    @Test
    @Order(1)
    void test_userController_to_give_401_while_create_user() {
        var userData = new UserModel(faker.name().firstName(), faker.name().lastName(), faker.internet().emailAddress(), UUID.randomUUID().toString(), null);
        when(userService.createUser(any(UserModel.class))).thenReturn(userData);
        HttpClientResponseException exception = assertThrows(HttpClientResponseException.class, () -> {
            client.toBlocking().exchange(HttpRequest.POST("/users", userData).header("Authorization", "Bearer " + jwt),UserModel.class);
        });
        assertEquals(HttpStatus.UNAUTHORIZED,exception.getStatus());
    }
    @Test
    @Order(2)
    void test_userController_to_create_user(){
        String jwt = getJwt();
        String firstName=faker.name().firstName();
        String lastName=faker.name().lastName();
        String email = faker.internet().emailAddress();
        var userData = new UserModel(firstName, lastName, email, UUID.randomUUID().toString(), null);
        when(userService.createUser(any(UserModel.class))).thenReturn(userData);
        HttpResponse<UserResponseDTO> response = client.toBlocking().exchange(HttpRequest.POST("/users",userData).bearerAuth(jwt), UserResponseDTO.class);
        UserResponseDTO myBody = response.getBody()
          .orElseThrow(() -> new RuntimeException("Response body is empty"));
        assertEquals(firstName,myBody.getFirstName());
        assertEquals(lastName,myBody.getLastName());
        assertEquals(email,myBody.getEmail());
        assertNotNull(myBody.getUid());
    }
    @Test
    @Order(3)
    void test_userController_firstName_blank(){
        String jwt = getJwt();
        System.out.println("JWT token: " + jwt);
        var userData = new UserModel("","World","my@test.com", UUID.randomUUID().toString(),null);
        HttpClientResponseException exception = assertThrows(HttpClientResponseException.class, () -> {
            client.toBlocking().exchange(HttpRequest.POST("/users", userData).header("Authorization", "Bearer " + jwt),UserModel.class);
        });
        assertEquals(HttpStatus.BAD_REQUEST,exception.getStatus());
    }

}