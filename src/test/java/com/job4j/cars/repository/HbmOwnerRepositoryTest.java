package com.job4j.cars.repository;

import com.job4j.cars.model.Owner;
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

class HbmOwnerRepositoryTest {

    private static SessionFactory sf;

    private static OwnerRepository ownerRepository;
    private static UserRepository userRepository;


    @BeforeAll
    private static void init() {
        sf = new HibernateConfiguration().sf();
        CrudRepository crudRepository = new CrudRepository(sf);
        ownerRepository = new HbmOwnerRepository(crudRepository);
        userRepository = new HbmUserRepository(crudRepository);
    }

    @AfterEach
    private void clear() {
        ownerRepository.findAll().forEach(owner -> ownerRepository.deleteById(owner.getId()));
        userRepository.findAll().forEach(user -> userRepository.deleteById(user.getId()));

    }

    @AfterAll
    private static void close() {
        sf.close();
    }

    @Test
    void whenSaveThenGetId() {
        User user = User.builder().login("login").password("password").build();
        userRepository.save(user);
        Owner owner = Owner.builder().user(user).name("owner").build();
        assertThat(ownerRepository.save(owner).orElseThrow().getId()).isEqualTo(owner.getId());
    }

    @Test
    void whenSaveThenFindById() {
        User user = User.builder().login("login").password("password").build();
        userRepository.save(user);
        Owner owner = Owner.builder().user(user).name("owner").build();
        int id = ownerRepository.save(owner).orElseThrow().getId();
        assertThat(ownerRepository.findById(id)).isEqualTo(Optional.of(owner));
    }

    @Test
    void whenGetEmptyEngineAndGetOptionalEmpty() {
        assertThat(ownerRepository.findById(-1)).isEmpty();
    }

    @Test
    void whenFindAll() {
        assertThat(ownerRepository.findAll()).isEmpty();
        User user = User.builder().login("login").password("password").build();
        userRepository.save(user);
        Owner owner = Owner.builder().user(user).name("owner").build();
        ownerRepository.save(owner);
        assertThat(ownerRepository.findAll()).isEqualTo(List.of(owner));
    }
}