package com.job4j.cars;

import com.job4j.cars.model.Post;
import com.job4j.cars.repository.*;
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
            PostRepository postRepository = new HbmPostRepository(crudRepository);


            Collection<Post> posts = postRepository.findByCar("car_1");
            posts.forEach(System.out::println);
            System.out.println("---------------------");
            for (Post p : posts) {
                System.out.println(p.getCar().getName());
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
