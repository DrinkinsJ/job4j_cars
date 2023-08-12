package com.job4j.cars.repository;

import com.job4j.cars.model.Engine;
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
public class HbmEngineRepository implements EngineRepository {

    CrudRepository crudRepository;

    @Override
    public Optional<Engine> save(Engine engine) {
        Optional<Engine> engineOptional = Optional.empty();
        try {
            crudRepository.run(session -> session.persist(engine));
            engineOptional = Optional.of(engine);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return engineOptional;
    }

    @Override
    public boolean update(Engine engine) {
        boolean res = false;
        try {
            crudRepository.run(session -> session.merge(engine));
            res = true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return res;
    }

    @Override
    public boolean deleteById(int id) {
        boolean res = false;
        try {
            crudRepository.run("DELETE FROM Engine WHERE id = :id",
                    Map.of("id", id));
            res = true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return res;
    }

    @Override
    public Optional<Engine> findById(int id) {
        Optional<Engine> engineOptional = Optional.empty();
        try {
            engineOptional = crudRepository.optional("FROM Engine Where id = Id", Engine.class,
                    Map.of("id", id));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return engineOptional;
    }

    @Override
    public Optional<Engine> findByName(String name) {
        Optional<Engine> engineOptional = Optional.empty();
        try {
            engineOptional = crudRepository.optional("FROM Engine Where name = :name", Engine.class,
                    Map.of("name", name));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return engineOptional;
    }

    @Override
    public Collection<Engine> findAll() {
        Collection<Engine> engines = Collections.emptyList();
        try {
            engines = crudRepository.query("FROM Engine", Engine.class);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return engines;
    }
}
