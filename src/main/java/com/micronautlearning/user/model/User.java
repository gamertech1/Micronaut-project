package com.micronautlearning.user.model;


import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.*;

@Introspected
@Serdeable
public class User{


    @Size(min = 3,max = 10)
    @NotEmpty
    private String firstName;

    @Size(min = 3,max = 10)
    @NotEmpty
    private String lastName;

    @Email
    @NotEmpty
    private String email;

    private String userId;

    public User(String firstName,String lastName,String email,String userId){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userId = userId;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName() {return lastName;}
    public String getEmail(){return email;}
    public String getId() {return userId;}
}
