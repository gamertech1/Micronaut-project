package com.micronautlearning.helloapp;

import io.micronaut.context.annotation.Primary;
import jakarta.inject.Singleton;

@Singleton
public class HelloUSA implements HelloMessage{

    @Override
    public String message() {
        return "Hello from USA";
    }
}
