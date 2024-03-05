package ru.kata.spring.boot_security.demo.servise;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;



public interface UserService {
    List<User> getAllUsers();

    public void addUser(User user);

    void deleteUser(Long id);

    User getUserById(Long id);

    void updateUser(User user);
}
