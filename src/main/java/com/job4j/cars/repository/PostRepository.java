package com.job4j.cars.repository;

import com.job4j.cars.model.Post;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Optional<Post> save(Post post);

    boolean update(Post post);

    boolean deleteById(int id);

    Optional<Post> findById(int id);

    Collection<Post> findAll();

    List<Post> findLastDayPost();

    List<Post> findPostWithPhoto();

    List<Post> findByBrand(String carName);
}
