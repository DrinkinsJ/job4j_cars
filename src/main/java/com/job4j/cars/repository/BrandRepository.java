package com.job4j.cars.repository;

import com.job4j.cars.model.Brand;

import java.util.Collection;
import java.util.Optional;

public interface BrandRepository {
    Optional<Brand> save(Brand brand);

    boolean update(Brand brand);

    boolean deleteById(int id);

    Optional<Brand> findById(int id);

    Collection<Brand> findAll();
}
