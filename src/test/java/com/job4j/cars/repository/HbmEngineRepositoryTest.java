package com.job4j.cars.repository;

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

public class HbmEngineRepositoryTest {
    private static SessionFactory sf;
    private static EngineRepository engineRepository;


    @BeforeAll
    private static void init() {
        sf = new HibernateConfiguration().sf();
        CrudRepository crudRepository = new CrudRepository(sf);
        engineRepository = new HbmEngineRepository(crudRepository);
    }

    @AfterEach
    private void clear() {
        engineRepository.findAll().forEach(engine -> engineRepository.deleteById(engine.getId()));
    }

    @AfterAll
    private static void close() {
        sf.close();
    }

    @Test
    void whenSaveThenGetId() {
        Engine engine = Engine.builder().model("engine").build();
        assertThat(engineRepository.save(engine).orElseThrow().getId()).isEqualTo(engine.getId());
    }

    @Test
    void whenSaveThenFindById() {
        Engine engine = Engine.builder().model("engine").build();
        int id = engineRepository.save(engine).orElseThrow().getId();
        assertThat(engineRepository.findById(id)).isEqualTo(Optional.of(engine));
    }

    @Test
    void whenGetEmptyEngineAndGetOptionalEmpty() {
        assertThat(engineRepository.findById(-1)).isEmpty();
    }

    @Test
    void whenFindAll() {
        engineRepository.findAll().forEach(engine -> engineRepository.deleteById(engine.getId()));
        Engine engine = Engine.builder().model("engine").build();
        engineRepository.save(engine);
        assertThat(engineRepository.findAll()).isEqualTo(List.of(engine));
    }
}