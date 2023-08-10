package com.job4j.cars.repository;

import com.job4j.cars.model.Owner;

import java.util.Collection;
import java.util.Optional;

public interface OwnerRepository {
    Optional<Owner> save(Owner owner);

    boolean update(Owner owner);

    boolean deleteById(int id);

    Optional<Owner> findById(int id);

    Collection<Owner> findAll();

}
