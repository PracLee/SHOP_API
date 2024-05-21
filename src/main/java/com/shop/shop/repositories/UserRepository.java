package com.shop.shop.repositories;

import com.shop.shop.models.User;
import com.shop.shop.models.UserId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, UserId> {
    boolean existsByEmail(String email);

    List<User> findAllByOrderByIdDesc();
}