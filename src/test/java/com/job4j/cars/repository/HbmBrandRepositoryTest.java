package com.job4j.cars.repository;

import com.job4j.cars.model.Brand;
import configuration.HibernateConfiguration;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HbmBrandRepositoryTest {
    private static SessionFactory sf;
    private static BrandRepository brandRepository;


    @BeforeAll
    private static void init() {
        sf = new HibernateConfiguration().sf();
        CrudRepository crudRepository = new CrudRepository(sf);
        brandRepository = new HbmBrandRepository(crudRepository);
    }

    @AfterEach
    private void clear() {
        brandRepository.findAll().forEach(brand -> brandRepository.deleteById(brand.getId()));
    }

    @AfterAll
    private static void close() {
        sf.close();
    }

    @Test
    void whenSaveThenGetId() {
        Brand brand = Brand.builder().brand("brand").build();
        assertThat(brandRepository.save(brand).orElseThrow().getId()).isEqualTo(brand.getId());
    }

    @Test
    void whenSaveThenFindById() {
        Brand brand = Brand.builder().brand("brand").build();
        int id = brandRepository.save(brand).orElseThrow().getId();
        assertThat(brandRepository.findById(id)).isEqualTo(Optional.of(brand));
    }

    @Test
    void whenGetEmptyEngineAndGetOptionalEmpty() {
        assertThat(brandRepository.findById(-1)).isEmpty();
    }

    @Test
    void whenFindAll() {
        assertThat(brandRepository.findAll()).isEmpty();
        Brand brand = Brand.builder().brand("brand").build();
        brandRepository.save(brand);
        assertThat(brandRepository.findAll()).isEqualTo(List.of(brand));
    }
}