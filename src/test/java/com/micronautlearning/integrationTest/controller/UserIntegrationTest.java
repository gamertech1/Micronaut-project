package com.micronautlearning.integrationTest.controller;

import com.micronautlearning.user.model.UserModel;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@MicronautTest(transactional = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Testcontainers
class UserIntegrationTest {

    @Container
    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @Inject
    @Client("/")
    HttpClient client;

    @BeforeAll
    void startContainerAndConfigureDatasource() {
        postgres.start();
        System.setProperty("datasources.default.url", postgres.getJdbcUrl());
        System.setProperty("datasources.default.username", postgres.getUsername());
        System.setProperty("datasources.default.password", postgres.getPassword());
    }

    @Test
    @Order(1)
    void testUserCreationAndStorage() {
        UserModel newUser = new UserModel("John","Doe","test@test.com","2345d5y354dcve5r",37);
        HttpRequest<UserModel> request = HttpRequest.POST("/users", newUser);
        HttpResponse<UserModel> response = client.toBlocking().exchange(request, UserModel.class);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatus());
        Assertions.assertNotNull(response.body());
        Assertions.assertNotNull(response.body().getUid());
    }
}
