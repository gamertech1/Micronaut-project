package com.micronautlearning.helloapp;

import io.micronaut.context.annotation.Primary;
import jakarta.inject.Singleton;

@Singleton
@Primary
public class HelloEU implements HelloMessage{
    @Override
    public String message() {
        return "Hello from EU";
    }
}
