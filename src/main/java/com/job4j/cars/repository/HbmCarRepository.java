package com.job4j.cars.repository;

import com.job4j.cars.model.Car;
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
public class HbmCarRepository implements CarRepository {

    private final CrudRepository crudRepository;

    @Override
    public Optional<Car> save(Car car) {
        Optional<Car> carOptional = Optional.empty();
        try {
            crudRepository.run(session -> session.persist(car));
            carOptional = Optional.of(car);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return carOptional;
    }

    @Override
    public boolean update(Car car) {
        boolean res = false;
        try {
            crudRepository.run(session -> session.merge(car));
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
            crudRepository.run("DELETE FROM Car WHERE id = :id",
                    Map.of("id", id));
            res = true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return res;
    }

    @Override
    public Optional<Car> findById(int id) {
        Optional<Car> carOptional = Optional.empty();
        try {
            carOptional = crudRepository.optional("FROM Car Where id = :id", Car.class,
                    Map.of("id", id));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return carOptional;
    }

    @Override
    public Collection<Car> findAll() {
        Collection<Car> cars = Collections.emptyList();
        try {
            cars = crudRepository.query("From Car", Car.class);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return cars;
    }
}
