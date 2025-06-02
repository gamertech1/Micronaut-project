package com.micronautlearning.helloapp;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;

@Controller("/hello")
public class HelloController {

    @Inject
    private final HelloMessage helloMessage;

    public HelloController(HelloMessage helloMessage) {
        this.helloMessage = helloMessage;
    }


    @Get(produces = MediaType.TEXT_PLAIN)
    public String helloworld(){
        return helloMessage.message();
    }
}
