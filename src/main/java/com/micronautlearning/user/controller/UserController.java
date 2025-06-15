package com.micronautlearning.user.controller;

import java.util.UUID;

import com.micronautlearning.user.model.UpdateUserDetail;
import com.micronautlearning.user.model.UserModel;
import com.micronautlearning.user.model.UserResponseDTO;
import com.micronautlearning.user.services.UserServiceImplementation;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import org.jsoup.Connection;

@Validated
@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/users")
public class UserController {

    private final UserServiceImplementation userServiceImplementation;

    public UserController(UserServiceImplementation userServiceImplementation) {
        this.userServiceImplementation = userServiceImplementation;
    }

    @Post("/")
//    @Operation(summary = "Create User",
//            requestBody = @RequestBody(
//            content= @Content(
//            mediaType = "application/json",
//            schema = @Schema(implementation = UpdateUserDetail.class))))
    public HttpResponse<UserResponseDTO> createUserAccount(@Body @Valid UserModel request) {
        System.out.println("Received user: " + request);
        if (request.getUid() == null) {
            request.setUid(UUID.randomUUID().toString());
        }
//        if (request.getFirstName() == null) {
//            throw new HttpStatusException(HttpStatus.BAD_REQUEST, "First name must not be null");
//
//        }
        UserModel user = userServiceImplementation.createUser(request);
        return HttpResponse.created(UserResponseDTO.from(user));
    }
    @Put("/{id}/update")
    public HttpResponse<UserResponseDTO> updateUserDetails(@PathVariable String id, @Body @Valid UpdateUserDetail request){
        UserModel user = userServiceImplementation.updateUser(id,request);
        return HttpResponse.ok(UserResponseDTO.from(user));
    }

    @Get("/{id}")
    public HttpResponse<UserResponseDTO> getUserDetails(@PathVariable String id){
        UserModel user = userServiceImplementation.getUser(id);
        return HttpResponse.ok(UserResponseDTO.from(user));
    }

}
