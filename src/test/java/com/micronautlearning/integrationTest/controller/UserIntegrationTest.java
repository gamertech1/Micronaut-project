package com.micronautlearning.integrationTest.controller;

import com.micronautlearning.user.data.NewUserRepository;
import com.micronautlearning.user.jwtToken.JwtResponseDTO;
import com.micronautlearning.user.jwtToken.LoginRequest;
import com.micronautlearning.user.model.NewUserModel;
import com.micronautlearning.user.model.UserModel;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.test.support.TestPropertyProvider;
import jakarta.inject.Inject;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Map;

@MicronautTest(transactional = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Testcontainers
class UserIntegrationTest implements TestPropertyProvider {

    @Container
    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("postgres")
            .withPassword("postgres")
            .withInitScript("init.sql");

    @Inject
    @Client("/")
    HttpClient client;

    @Inject
    NewUserRepository newUserRepository;

    // Override datasource properties for Micronaut to use Testcontainers DB
    @Override
    public Map<String, String> getProperties() {
        postgres.start();
        return Map.of(
                "datasources.default.url", postgres.getJdbcUrl(),
                "datasources.default.username", postgres.getUsername(),
                "datasources.default.password", postgres.getPassword(),
                "jpa.default.properties.hibernate.hbm2ddl.auto", "update",
                "jpa.default.properties.hibernate.create_schemas", "true"
        );
    }

    @BeforeAll
    void setupInitialData() {
        // Insert test user if not exists
        if (newUserRepository.findByUserName("JohnCena").isEmpty()) {
            NewUserModel user = new NewUserModel();
            user.setUserName("JohnCena");
            user.setPassword("12345678");
            user.setRepeatPassword("12345678");
            newUserRepository.save(user);
        }
    }

    private String getJwt() {
        var authData = new LoginRequest("JohnCena","12345678");
        return client.toBlocking()
                .exchange(HttpRequest.POST("/auth/login", authData), JwtResponseDTO.class)
                .body().getAccess_token();
    }

    @Test
    @Order(1)
    void testUserCreationAndStorage() {
        String jwt = getJwt();
        UserModel newUser = new UserModel("John","Doe","test@test.com","2345d5y354dcve5r",null);
        HttpRequest<UserModel> request = HttpRequest.POST("/users", newUser).bearerAuth(jwt);
        HttpResponse<UserModel> response = client.toBlocking().exchange(request, UserModel.class);
        try {
            Thread.sleep(10 * 60 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatus());
        Assertions.assertNotNull(response.body());
        Assertions.assertNotNull(response.body().getUid());
        System.out.println("Test finished. Container still running...");

    }
}
