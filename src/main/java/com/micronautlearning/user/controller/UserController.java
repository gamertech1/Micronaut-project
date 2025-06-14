package com.micronautlearning.user.controller;

import java.util.UUID;

import com.micronautlearning.user.model.UserModel;
import com.micronautlearning.user.services.UserServiceImplementation;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.validation.Validated;
import jakarta.validation.Valid;

@Validated
@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/users")
public class UserController {

    private final UserServiceImplementation userServiceImplementation;

    public UserController(UserServiceImplementation userServiceImplementation) {
        this.userServiceImplementation = userServiceImplementation;
    }

    @Post("/")
    public HttpResponse<UserModel> createUserAccount(@Body @Valid UserModel request) {
        System.out.println("Received user: " + request);
        if (request.getId() == null) {
            request.setId(UUID.randomUUID().toString());
        }
//        if (request.getFirstName() == null) {
//            throw new HttpStatusException(HttpStatus.BAD_REQUEST, "First name must not be null");
//
//        }

        UserModel user = userServiceImplementation.createUser(request);
        return HttpResponse.created(user);
    }

}
