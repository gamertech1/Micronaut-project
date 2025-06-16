package com.micronautlearning.user.data;

import com.micronautlearning.user.model.NewUserModel;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface NewUserRepository extends CrudRepository<NewUserModel, String> {
    Optional<NewUserModel> findByUserName(String userName);
}
