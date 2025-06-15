package com.micronautlearning.user.model;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class UserResponseDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String uid;

    public UserResponseDTO(String firstName, String lastName, String email, String uid) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.uid = uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
    public static UserResponseDTO from (UserModel user) {
        return new UserResponseDTO(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getUid()
        );
    }
}
