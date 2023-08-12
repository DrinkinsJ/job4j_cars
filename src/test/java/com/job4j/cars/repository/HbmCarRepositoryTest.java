package com.job4j.cars.repository;

import com.job4j.cars.model.Car;
import com.job4j.cars.model.Engine;
import com.job4j.cars.model.Owner;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;


public class HbmCarRepositoryTest {
    private static StandardServiceRegistry registry;
    private static SessionFactory sf;
    private static CarRepository carRepository;
    private static EngineRepository engineRepository;

    @BeforeAll
    public static void initServices() {
        registry = new StandardServiceRegistryBuilder().configure().build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        carRepository = new HbmCarRepository(new CrudRepository(sf));
        engineRepository = new HbmEngineRepository(new CrudRepository(sf));
    }

    @AfterAll
    public static void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    @AfterEach
    public void clear() {
        var cars = carRepository.findAll();
        cars.forEach(car -> carRepository.deleteById(car.getId()));
    }

    @Test
    public void whenSaveThenReturnOptional() {
        Set<Owner> owners = new HashSet<>();
        Engine engine = new Engine("engine_1");
        Car car = new Car("car_1", engine, owners);
        var actualCar = carRepository.save(car);
        assertThat(actualCar).isEqualTo(Optional.of(car));
    }

    @Test
    public void whenUpdateThenReturnTrue() {
        Set<Owner> owners = new HashSet<>();
        Engine engine = new Engine("engine_1");
        Car car = new Car("car_1", engine, owners);
        carRepository.save(car);
        car.setEngine(new Engine("engine_update"));
        boolean engineUpd = carRepository.update(car);
        assertThat(engineUpd).isTrue();
        car.setName("car_update");
        boolean carName = carRepository.update(car);
        assertThat(carName).isTrue();
    }
}