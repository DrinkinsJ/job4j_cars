package com.job4j.cars.repository;

import com.job4j.cars.model.Brand;
import com.job4j.cars.model.Car;
import com.job4j.cars.model.Engine;
import configuration.HibernateConfiguration;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class HbmCarRepositoryTest {
    private static SessionFactory sf;
    private static CarRepository carRepository;
    private static EngineRepository engineRepository;

    private static BrandRepository brandRepository;



    @BeforeAll
    private static void init() {
        sf = new HibernateConfiguration().sf();
        CrudRepository crudRepository = new CrudRepository(sf);
        carRepository = new HbmCarRepository(crudRepository);
        engineRepository = new HbmEngineRepository(crudRepository);
        brandRepository = new HbmBrandRepository(crudRepository);
    }

    @AfterEach
    private void clear() {
        carRepository.findAll().forEach(car -> carRepository.deleteById(car.getId()));
        brandRepository.findAll().forEach(brand -> brandRepository.deleteById(brand.getId()));
    }

    @AfterAll
    private static void close() {
        sf.close();
    }

    @Test
    void whenSaveThenGetId() {
        Engine engine = Engine.builder().model("model").build();
        engineRepository.save(engine);
        Brand brand = Brand.builder().brand("brand").build();
        brandRepository.save(brand);
        Car car = Car.builder().engine(engine).brand(brand).model("model").build();
        carRepository.save(car);
        assertThat(carRepository.findById(car.getId())).isEqualTo(Optional.of(car));
    }

    @Test
    void whenSaveThenUpdate() {
        Engine engine = Engine.builder().model("model").build();
        engineRepository.save(engine);
        Brand brand = Brand.builder().brand("brand").build();
        brandRepository.save(brand);
        Car car = Car.builder().engine(engine).brand(brand).model("model").build();
        carRepository.save(car);

        car.setModel("newModel");
        carRepository.update(car);
        assertThat(carRepository.findById(car.getId()).get().getModel()).isEqualTo("newModel");
    }

    @Test
    void whenFindAll() {
        assertThat(carRepository.findAll()).isEmpty();
        Engine engine = Engine.builder().model("model").build();
        engineRepository.save(engine);
        Brand brand = Brand.builder().brand("brand").build();
        brandRepository.save(brand);
        Car car = Car.builder().engine(engine).brand(brand).model("model").build();
        carRepository.save(car);
        assertThat(carRepository.findAll()).isEqualTo(List.of(car));
    }
}