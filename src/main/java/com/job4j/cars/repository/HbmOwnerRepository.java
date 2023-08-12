package com.job4j.cars.repository;

import com.job4j.cars.model.Owner;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Repository
@Slf4j
@AllArgsConstructor
public class HbmOwnerRepository implements OwnerRepository {
    CrudRepository crudRepository;

    @Override
    public Optional<Owner> save(Owner owner) {
        Optional<Owner> engineOptional = Optional.empty();
        try {
            crudRepository.run(session -> session.persist(owner));
            engineOptional = Optional.of(owner);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return engineOptional;
    }

    @Override
    public boolean update(Owner owner) {
        boolean res = false;
        try {
            crudRepository.run(session -> session.merge(owner));
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
            crudRepository.run("DELETE FROM Owner WHERE id = :id",
                    Map.of("id", id));
            res = true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return res;
    }

    @Override
    public Optional<Owner> findById(int id) {
        Optional<Owner> ownerOptional = Optional.empty();
        try {
            ownerOptional = crudRepository.optional("FROM Owner Where id = Id", Owner.class,
                    Map.of("id", id));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return ownerOptional;
    }

    @Override
    public Collection<Owner> findAll() {
        Collection<Owner> owners = Collections.emptyList();
        try {
            owners = crudRepository.query("FROM Owner", Owner.class);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return owners;
    }
}
