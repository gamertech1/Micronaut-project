package com.micronautlearning.provider;

import jakarta.inject.Singleton;

@Singleton
public class FileLogger implements Logger {

    @Override
    public void log() {
        System.out.println("Logging message for file");
    }
}
