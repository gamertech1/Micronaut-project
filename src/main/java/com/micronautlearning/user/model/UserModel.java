package com.micronautlearning.user.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.inject.Singleton;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import java.util.Set;
import java.util.UUID;
//
@Entity
@Table(name = "users",schema = "mn")
@Access(AccessType.FIELD)
@Serdeable
@Introspected
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
public class UserModel {

    @NotBlank(message = "First name must not be blank")
    @Size(min = 3, max = 10)
    @Schema(example = "John")
    @Column(nullable = false, length = 25, name = "first_name")
    private String firstName;

    @Size(min = 3, max = 10)
    @NotBlank
    @Column(nullable = false, length = 25, name = "last_name")
    private String lastName;

    @Email
    @NotBlank
    @Column(unique = true, nullable = false, name = "email")
    private String email;

//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private String id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // or SEQUENCE
    private Integer userCount;


    public UserModel() {}

    public UserModel(String firstName, String lastName, String email, String
            id,Integer userCount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.id = id;
        this.userCount = userCount;
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }
    public static void main(String[] args) {
        UserModel userData = new UserModel("", "", "my", UUID.randomUUID().toString(),22);

        ValidatorFactory factory = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory();

        Validator validator = factory.getValidator();

        Set<ConstraintViolation<UserModel>> violations = validator.validate(userData);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<UserModel> violation : violations) {
                System.out.println(violation.getPropertyPath() + " - " + violation.getMessage());
            }
        } else {
            System.out.println("No validation errors.");
        }
    }
}
