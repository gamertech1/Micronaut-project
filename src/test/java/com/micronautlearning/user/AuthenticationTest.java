package com.micronautlearning.user;

import com.micronautlearning.helloapp.HelloUSA;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.security.token.render.BearerAccessRefreshToken;
import io.micronaut.serde.annotation.SerdeImport;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.micronautlearning.Application.LOG;
import static io.micronaut.http.HttpRequest.*;
import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public class AuthenticationTest {

    @Inject
    @Client("/")
    HttpClient client;

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationTest.class);


    @Test
    void unauthorizedValidation(){
        try{
            client.toBlocking().retrieve("/hello");
            fail("Should fail if no exception is thrown");
        } catch (HttpClientResponseException e){
            assertEquals(HttpStatus.UNAUTHORIZED,e.getStatus());
        }
    }
    @Test
    void test_The_UserController_For_Authentication(){
        final BearerAccessRefreshToken token = getBearerAccessRefreshToken();
        var request = GET("/hello")
                .accept(MediaType.APPLICATION_JSON)
                .bearerAuth(token.getAccessToken());
        var result =client.toBlocking().retrieve(request);
        assertNotNull(result);
        assertEquals("Hello from EU",result);
    }

    @NotNull
    private BearerAccessRefreshToken getBearerAccessRefreshToken() {
        final UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("john", "password");
        var login = HttpRequest.POST("/auth/login",credentials);
        var response = client.toBlocking().exchange(login, BearerAccessRefreshToken.class);
        assertEquals(HttpStatus.OK,response.status());
        final BearerAccessRefreshToken token = response.body();
        assertNotNull(token);
        assertEquals("john",token.getUsername());
        LOG.debug("Login Bearer token: {} expires in {}",token.getAccessToken(),token.getExpiresIn());
        return token;
    }
}
