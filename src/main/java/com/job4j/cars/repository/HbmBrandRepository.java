package com.job4j.cars.repository;

import com.job4j.cars.model.Brand;
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
public class HbmBrandRepository implements BrandRepository {

    private final CrudRepository crudRepository;

    @Override
    public Optional<Brand> save(Brand brand) {
        Optional<Brand> carOptional = Optional.empty();
        try {
            crudRepository.run(session -> session.persist(brand));
            carOptional = Optional.of(brand);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return carOptional;
    }

    @Override
    public boolean update(Brand brand) {
        boolean res = false;
        try {
            crudRepository.run(session -> session.merge(brand));
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
            crudRepository.run("DELETE FROM Brand WHERE id = :id",
                    Map.of("id", id));
            res = true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return res;
    }

    @Override
    public Optional<Brand> findById(int id) {
        Optional<Brand> brandOptional = Optional.empty();
        try {
            brandOptional = crudRepository.optional("FROM Brand Where id = :id", Brand.class,
                    Map.of("id", id));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return brandOptional;
    }

    @Override
    public Collection<Brand> findAll() {
        Collection<Brand> brands = Collections.emptyList();
        try {
            brands = crudRepository.query("From Brand", Brand.class);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return brands;
    }
}
