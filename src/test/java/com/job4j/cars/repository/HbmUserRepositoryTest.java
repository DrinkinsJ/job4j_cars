package com.job4j.cars.repository;

import com.job4j.cars.model.User;
import configuration.HibernateConfiguration;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class HbmUserRepositoryTest {

    private static SessionFactory sf;
    private static UserRepository userRepository;

    @BeforeAll
    private static void init() {
        sf = new HibernateConfiguration().sf();
        CrudRepository crudRepository = new CrudRepository(sf);
        userRepository = new HbmUserRepository(crudRepository);
        userRepository.findAll().forEach(user -> userRepository.deleteById(user.getId()));
    }

    @AfterEach
    private void clear() {
        userRepository.findAll().forEach(user -> userRepository.deleteById(user.getId()));
    }

    @AfterAll
    private static void close() {
        sf.close();
    }

    @Test
    void whenSaveThenGetId() {
        User user = User.builder().login("login").password("password").build();
        assertThat(userRepository.save(user).orElseThrow().getId()).isEqualTo(user.getId());
    }

    @Test
    void whenSaveThenFindById() {
        User user = User.builder().login("login").password("password").build();
        int id = userRepository.save(user).orElseThrow().getId();
        assertThat(userRepository.findById(id)).isEqualTo(Optional.of(user));
    }

    @Test
    void whenGetEmptyEngineAndGetOptionalEmpty() {
        assertThat(userRepository.findById(-1)).isEmpty();
    }

    @Test
    void whenFindAll() {
        assertThat(userRepository.findAll()).isEmpty();
        User user = User.builder().login("login").password("password").build();
        userRepository.save(user);
        assertThat(userRepository.findAll()).isEqualTo(List.of(user));
    }
}