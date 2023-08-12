package com.job4j.cars.repository;

import com.job4j.cars.model.Engine;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class HbmEngineRepositoryTest {

    private static StandardServiceRegistry registry;
    private static SessionFactory sf;
    private static HbmEngineRepository repository;

    @BeforeAll
    private static void init() {
        registry = new StandardServiceRegistryBuilder().configure().build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        repository = new HbmEngineRepository(new CrudRepository(sf));
    }

    @AfterAll
    private static void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    @AfterEach
    private void clear() {
        var engines = repository.findAll();
        engines.forEach(engine -> repository.deleteById(engine.getId()));
    }

    @Test
    public void whenSaveSuccessThenReturnOptional() {
        Engine engine = new Engine("engine_1");
        var actualEngine = repository.save(engine);
        assertThat(actualEngine).isEqualTo(Optional.of(engine));
    }

    @Test
    public void whenUpdateThenReturnTrue() {
        Engine engine = new Engine("engine_1");
        repository.save(engine);
        engine.setName("engine_update");
        boolean isUpdated = repository.update(engine);
        assertThat(isUpdated).isTrue();
    }

    @Test
    public void whenDeleteByIdThenReturnTrue() {
        Engine engine = new Engine("engine_1");
        repository.save(engine);
        var actualEngine = repository.findByName(engine.getName());
        boolean isDeleted = repository.deleteById(actualEngine.orElseThrow().getId());
        assertThat(isDeleted).isTrue();
    }


}