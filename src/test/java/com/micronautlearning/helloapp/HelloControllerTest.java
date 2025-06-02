package com.micronautlearning.helloapp;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class HelloControllerTest {

    public static final Logger LOG = LoggerFactory.getLogger(HelloControllerTest.class);
    @Inject
    @Client("/")
    HttpClient httpClient;

    @Test
    void testingHelloControllerData(){
        var response =  httpClient.toBlocking().retrieve("/hello");
        LOG.trace("Response: {}",response);
        assertEquals("Hello from EU",response);
    }

//    @Test
//    void testingStatusCode(){
//        var response = httpClient.toBlocking().exchange("/hello",String.class);
//        assertEquals(HttpStatus.OK,response.getStatus());
//        assertEquals("Hello World",response.getBody().get());
//    }

}