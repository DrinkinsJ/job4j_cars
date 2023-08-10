package com.job4j.cars.repository;

import com.job4j.cars.model.User;
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
public class HbmUserRepository implements UserRepository {
    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     *
     * @param user пользователь.
     * @return пользователь с id.
     */
    @Override
    public Optional<User> save(User user) {
        crudRepository.run(session -> session.persist(user));
        return Optional.ofNullable(user);
    }


    /**
     * Обновить в базе пользователя.
     *
     * @param user пользователь.
     */
    @Override
    public boolean update(User user) {
        boolean res = false;
        try {
            crudRepository.run(session -> session.merge(user));
            res = true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return res;
    }

    /**
     * Удалить пользователя по id.
     *
     * @param userId ID
     * @return res Result
     */
    @Override
    public boolean deleteById(int userId) {
        boolean res = false;
        try {
            crudRepository.run(
                    "delete from User where id = :fId",
                    Map.of("fId", userId)
            );
            res = true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return res;
    }

    /**
     * Найти пользователя по ID
     *
     * @return пользователь.
     */
    @Override
    public Optional<User> findById(int id) {
        return crudRepository.optional(
                "from User where id = :fId", User.class,
                Map.of("fId", id)
        );
    }

    @Override
    public Collection<User> findAll() {
        Collection<User> users = Collections.emptyList();
        try {
            users = crudRepository.query("SELECT FROM User", User.class);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return users;
    }
}
