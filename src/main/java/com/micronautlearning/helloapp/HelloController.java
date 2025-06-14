package com.micronautlearning.helloapp;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;
@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/hello")
public class HelloController {

    @Inject
    private final HelloMessage helloMessage;

    public HelloController(HelloMessage helloMessage) {
        this.helloMessage = helloMessage;
    }


    @Get(produces = MediaType.APPLICATION_JSON)
    public String helloWorld(){
        return helloMessage.message();
    }

}
