package com.micronautlearning.user.data;
import com.micronautlearning.user.model.UserModel;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<UserModel,Integer> {
//    UserModel save(UserModel userModel);
Optional<UserModel> findByUid(String uid);
    void deleteByUid(String uid);

}
