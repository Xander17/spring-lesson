package ru.geekbrains.repositories;

import org.springframework.stereotype.Repository;
import ru.geekbrains.entities.Product;
import ru.geekbrains.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class UserRepository {

    private AtomicInteger lastId;

    private Map<Long, User> users;

    public UserRepository() {
        lastId = new AtomicInteger(0);
        users = new ConcurrentHashMap<>();

        add(new User(-1, "Name1", "Pa$$worD1"));
        add(new User(-1, "Name2", "Pa$$worD2"));
        add(new User(-1, "Name3", "Pa$$worD3"));
        add(new User(-1, "Name4", "Pa$$worD4"));
        add(new User(-1, "Name5", "Pa$$worD5"));
    }

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    public User findById(long id) {
        return users.get(id);
    }

    public void add(User user) {
        long id = lastId.incrementAndGet();
        user.setId(id);
        users.put(id, user);
    }
}
