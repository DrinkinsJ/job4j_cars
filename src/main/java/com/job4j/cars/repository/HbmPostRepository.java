package com.job4j.cars.repository;

import com.job4j.cars.model.Post;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
@AllArgsConstructor
@Slf4j
public class HbmPostRepository implements PostRepository {

    private final CrudRepository crudRepository;

    @Override
    public Optional<Post> save(Post post) {
        Optional<Post> postOptional = Optional.empty();
        try {
            crudRepository.run(session -> session.persist(post));
            postOptional = Optional.of(post);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return postOptional;
    }

    @Override
    public boolean update(Post post) {
        boolean res = false;
        try {
            crudRepository.run(session -> session.merge(post));
            res = true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return res;
    }

    @Override
    public boolean deleteById(int id) {
        boolean res = false;
        try {
            crudRepository.run("DELETE FROM Post WHERE id = :id",
                    Map.of("id", id));
            res = true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return res;
    }

    @Override
    public Collection<Post> findAll() {
        Collection<Post> posts = Collections.emptyList();
        try {
            posts = crudRepository.query("FROM Post p JOIN FETCH p.photos", Post.class);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return posts;
    }

    @Override
    public Optional<Post> findById(int id) {
        Optional<Post> postOptional = Optional.empty();
        try {
            postOptional = crudRepository.optional("FROM Post Where id = Id", Post.class,
                    Map.of("id", id));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return postOptional;
    }

    @Override
    public List<Post> findLastDayPost() {
        LocalDateTime time = LocalDateTime.now().minusDays(1);
        List<Post> posts = new ArrayList<>();
        try {
            posts = crudRepository.query("FROM Post WHERE created >= :time", Post.class,
                    Map.of("time", time));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return posts;
    }

    @Override
    public List<Post> findPostWithPhoto() {

        List<Post> posts = new ArrayList<>();
        try {
            posts = crudRepository.query("SELECT DISTINCT p FROM Post p JOIN FETCH p.photos WHERE p.photos IS NOT EMPTY", Post.class);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return posts;
    }

    @Override
    public List<Post> findByCar(String carName) {
        return crudRepository.query("FROM Post p WHERE p.car.name = :fName",
                Post.class, Map.of("fName", carName));
    }
}
