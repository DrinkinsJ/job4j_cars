package com.job4j.cars.repository;

import com.job4j.cars.model.File;

import java.util.Collection;
import java.util.Optional;

public interface FileRepository {
    Optional<File> save(File file);

    boolean update(File file);

    boolean deleteById(int id);

    Optional<File> findById(int id);

    Collection<File> findAll();
}
