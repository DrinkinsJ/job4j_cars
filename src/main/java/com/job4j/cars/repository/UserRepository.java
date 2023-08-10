package com.job4j.cars.repository;


import com.job4j.cars.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository {
    Optional<User> save(User user);

    boolean update(User user);

    boolean deleteById(int id);

    Optional<User> findById(int id);

    Collection<User> findAll();
}
