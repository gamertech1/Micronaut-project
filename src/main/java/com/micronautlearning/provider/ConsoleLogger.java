package com.micronautlearning.provider;

import jakarta.inject.Singleton;

@Singleton
public class ConsoleLogger implements Logger{
    @Override
    public void log() {
        System.out.println("logging messages for console");
    }
}
