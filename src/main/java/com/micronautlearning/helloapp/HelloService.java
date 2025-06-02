package com.micronautlearning.helloapp;

import jakarta.inject.Singleton;

@Singleton
public class HelloService {

    private final HelloMessage helloMessage;

    public HelloService(HelloMessage helloMessage) {
        this.helloMessage = helloMessage;
    }
    void runHelloService(){
        System.out.println("Running hello service");
        helloMessage.message();
    }
}
