package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {

    List<User> getAllUsers();


    void addUser(User user);


    void deleteUser(Long id);


    User getUserById(Long id);

    void updateUser(User user);
}
