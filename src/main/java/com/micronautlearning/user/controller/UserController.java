package com.micronautlearning.user.controller;

import com.micronautlearning.user.model.User;
import com.micronautlearning.user.services.UserServiceImplementation;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;
import jakarta.validation.Valid;

import java.util.List;

@Controller("/users")
public class UserController {

    @Inject
    UserServiceImplementation userServiceImplementation;

    public UserController(UserServiceImplementation userServiceImplementation) {
        this.userServiceImplementation = userServiceImplementation;
    }


    @Post()
    public HttpResponse<User> createUserAccount(@Body @Valid User request){
        User user =  userServiceImplementation.createUser(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                null);
        return HttpResponse.created(user);
    }





}
