package com.micronautlearning.user.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usersDetail",schema = "mn")
@Access(AccessType.FIELD)
@Serdeable
@Introspected
public class NewUserModel {

    @Id
    @NotBlank
    private String userName;
    @Size(min = 8,max = 16)
    private String password;
    @Size(min = 8,max = 16)
    private String repeatPassword;

    public NewUserModel(String userName, String password, String repeatPassword) {
        this.userName = userName;
        this.password = password;
        this.repeatPassword = repeatPassword;
    }

    public NewUserModel() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
