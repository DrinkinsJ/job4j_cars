package com.job4j.cars.repository;


import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

@AllArgsConstructor
public class HbmPostRepositoryTest {

    private static StandardServiceRegistry registry;
    private static SessionFactory sf;
    private static PostRepository repository;

    @BeforeAll
    private static void init() {
        registry = new StandardServiceRegistryBuilder().configure().build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        repository = new HbmPostRepository(new CrudRepository(sf));
    }

    @BeforeAll
    private static void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    @Test
    public void whenGetPostWithPhoto() {
        var posts = repository.findAll();
        posts.forEach(System.out::println);
    }
}