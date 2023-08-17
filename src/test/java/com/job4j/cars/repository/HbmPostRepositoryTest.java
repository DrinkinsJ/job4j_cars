package com.job4j.cars.repository;

import com.job4j.cars.model.*;
import configuration.HibernateConfiguration;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HbmPostRepositoryTest {

    private static SessionFactory sf;
    private static CarRepository carRepository;
    private static EngineRepository engineRepository;
    private static UserRepository userRepository;

    private static PostRepository postRepository;
    private static FileRepository fileRepository;

    private static BrandRepository brandRepository;


    @BeforeAll
    private static void init() {
        sf = new HibernateConfiguration().sf();
        CrudRepository crudRepository = new CrudRepository(sf);
        carRepository = new HbmCarRepository(crudRepository);
        engineRepository = new HbmEngineRepository(crudRepository);
        userRepository = new HbmUserRepository(crudRepository);
        postRepository = new HbmPostRepository(crudRepository);
        fileRepository = new HbmFileRepository(crudRepository);
        brandRepository = new HbmBrandRepository(crudRepository);
    }

    @AfterEach
    private void clear() {
        postRepository.findAll().forEach(post -> postRepository.deleteById(post.getId()));
        carRepository.findAll().forEach(car -> carRepository.deleteById(car.getId()));
        userRepository.findAll().forEach(user -> userRepository.deleteById(user.getId()));
        brandRepository.findAll().forEach(brand -> brandRepository.deleteById(brand.getId()));
        fileRepository.findAll().forEach(file -> fileRepository.deleteById(file.getId()));
    }

    @AfterAll
    private static void close() {
        sf.close();
    }

    @Test
    void whenSaveThenGetId() {
        LocalDateTime time = LocalDateTime.now();
        File file = File.builder().fileName("file").path("path").build();
        fileRepository.save(file);

        Engine engine = Engine.builder().model("engine").build();
        engineRepository.save(engine);
        Brand brand = Brand.builder().brand("brand").build();
        brandRepository.save(brand);
        Car car = Car.builder().engine(engine).brand(brand).model("car").build();
        carRepository.save(car);

        User user = User.builder().login("login1").password("password1").build();
        userRepository.save(user);

        Post post = Post.builder()
                .file(file)
                .description("desc")
                .price(300)
                .created(time)
                .user(user)
                .isSold(false)
                .car(car).build();

        postRepository.save(post);

       assertThat(postRepository.findById(post.getId())).isEqualTo(Optional.of(post));
    }

    @Test
    public void whenFindLastDayPost() {
        LocalDateTime time = LocalDateTime.now();
        File file = File.builder().fileName("file").path("path").build();
        fileRepository.save(file);

        Engine engine = Engine.builder().model("engine").build();
        engineRepository.save(engine);
        Brand brand = Brand.builder().brand("brand").build();
        brandRepository.save(brand);
        Car car = Car.builder().engine(engine).brand(brand).model("car").build();
        carRepository.save(car);

        User user1 = User.builder().login("login1").password("password1").build();
        userRepository.save(user1);
        User user2 = User.builder().login("login2").password("password2").build();
        userRepository.save(user2);

        Post newPost = Post.builder()
                .file(file)
                .description("desc")
                .price(300)
                .created(time)
                .user(user1)
                .isSold(false)
                .car(car).build();

        Post oldPost = Post.builder()
                .file(file)
                .description("desc")
                .price(300)
                .created(time.minusDays(2))
                .user(user2)
                .isSold(false)
                .car(car).build();

        postRepository.save(oldPost);
        postRepository.save(newPost);

        assertThat(postRepository.findLastDayPost()).isEqualTo(List.of(newPost));
    }

    @Test
    public void whenFindPostsWithPhoto() {
        LocalDateTime time = LocalDateTime.now();
        File file = File.builder().fileName("file").path("path").build();
        fileRepository.save(file);

        Engine engine = Engine.builder().model("engine").build();
        engineRepository.save(engine);
        Brand brand = Brand.builder().brand("brand").build();
        brandRepository.save(brand);
        Car car = Car.builder().brand(brand).engine(engine).model("car").build();
        carRepository.save(car);

        User user1 = User.builder().login("login1").password("password1").build();
        userRepository.save(user1);
        User user2 = User.builder().login("login2").password("password2").build();
        userRepository.save(user2);

        Post photoPost = Post.builder()
                .file(file)
                .description("desc")
                .price(300)
                .created(time)
                .user(user1)
                .isSold(false)
                .car(car).build();

        Post noPhotoPost = Post.builder()
                .description("desc")
                .price(300)
                .created(time)
                .user(user2)
                .isSold(false)
                .car(car).build();

        postRepository.save(photoPost);
        postRepository.save(noPhotoPost);

        assertThat(postRepository.findPostWithPhoto()).isEqualTo(List.of(photoPost));
    }

    @Test
    public void whenFindPostsSelectedBrand() {
        LocalDateTime time = LocalDateTime.now();
        File file = File.builder().fileName("file").path("path").build();
        fileRepository.save(file);

        Engine engine = Engine.builder().model("engine").build();
        engineRepository.save(engine);
        Brand brand = Brand.builder().brand("brand").build();
        brandRepository.save(brand);
        Car car = Car.builder().engine(engine).brand(brand).model("car").build();
        carRepository.save(car);

        User user1 = User.builder().login("login1").password("password1").build();
        userRepository.save(user1);
        User user2 = User.builder().login("login2").password("password2").build();
        userRepository.save(user2);

        Post photoPost = Post.builder()
                .file(file)
                .description("desc")
                .price(300)
                .created(time)
                .user(user1)
                .isSold(false)
                .car(car).build();

        Post noPhotoPost = Post.builder()
                .description("desc")
                .price(300)
                .created(time)
                .user(user2)
                .isSold(false)
                .car(car).build();

        postRepository.save(photoPost);
        postRepository.save(noPhotoPost);

        assertThat(postRepository.findPostWithPhoto()).isEqualTo(List.of(photoPost));
    }
}