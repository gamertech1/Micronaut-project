package com.micronautlearning.provider;

import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Singleton
public class MyApplication {

     private final Logger logger;

    public MyApplication(@Named("console") Logger logger) {
        this.logger = logger;
    }
    void process(){
        System.out.println("Processing the logs");
        logger.log();
    }
}
