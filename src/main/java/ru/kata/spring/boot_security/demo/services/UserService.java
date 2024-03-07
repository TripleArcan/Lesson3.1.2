package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;



public interface UserService extends UserDetailsService {
    List<User> getAllUsers();

    void addUser(User user);

    void deleteUser(String username);

    void updateUser(User user);

    User getUserByUsername(String username);
}
