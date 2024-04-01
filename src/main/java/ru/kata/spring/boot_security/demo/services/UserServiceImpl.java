package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UsersRepository;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;


    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }


    @Override
    @Transactional
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        usersRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(String name) {
        usersRepository.deleteByUsername(name);
    }

    @Override
    public String setPasswordEncoder(String password) {
        return passwordEncoder.encode(password);
    }


    @Override
    @Transactional
    public void updateUser(User user) {
        usersRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> optionalUser = usersRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            user.getRoles().size();
            return user;
        } else {
            throw new UsernameNotFoundException("Пользователь с id " + id + " не найден");
        }
    }

    @Override
    public User getUserByUsername(String name) {
        return usersRepository.findByUsername(name);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }


}
