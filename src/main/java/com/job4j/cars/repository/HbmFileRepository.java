package com.job4j.cars.repository;

import com.job4j.cars.model.File;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Slf4j
public class HbmFileRepository implements FileRepository {

    private final CrudRepository crudRepository;

    @Override
    public Optional<File> save(File file) {
        Optional<File> fileOptional = Optional.empty();
        try {
            crudRepository.run(session -> session.persist(file));
            fileOptional = Optional.of(file);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return fileOptional;
    }

    @Override
    public boolean update(File file) {
        boolean res = false;
        try {
            crudRepository.run(session -> session.merge(file));
            res = true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return res;
    }

    @Override
    public boolean deleteById(int id) {
        boolean res = false;
        try {
            crudRepository.run("DELETE FROM File WHERE id = :id",
                    Map.of("id", id));
            res = true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return res;
    }

    @Override
    public Optional<File> findById(int id) {
        Optional<File> fileOptional = Optional.empty();
        try {
            fileOptional = crudRepository.optional("FROM File Where id = :id", File.class,
                    Map.of("id", id));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return fileOptional;
    }

    @Override
    public Collection<File> findAll() {
        Collection<File> files = Collections.emptyList();
        try {
            files = crudRepository.query("FROM File", File.class);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return files;
    }
}
