package com.micronautlearning.helloapp;

import jakarta.inject.Singleton;

@Singleton
public interface HelloMessage {
    String message();
}
