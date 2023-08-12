package com.job4j.cars;

import com.job4j.cars.model.Engine;
import com.job4j.cars.repository.CrudRepository;
import com.job4j.cars.repository.EngineRepository;
import com.job4j.cars.repository.HbmEngineRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Collection;

@Slf4j
public class HmbRunner {

    public static void main(String[] args) {

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            CrudRepository crudRepository = new CrudRepository(sf);
            EngineRepository engineRepository = new HbmEngineRepository(crudRepository);

            Collection<Engine> engines = engineRepository.findAll();

            for (Engine engine : engines) {
                System.out.println(engine);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
