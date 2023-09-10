package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    void add(User user);
    List<User> getAllUsers();
    User getUser(long id);
    void deleteUser(long id);
    void updateUser(long id, User user);
    User findByUserName(String name);
}
