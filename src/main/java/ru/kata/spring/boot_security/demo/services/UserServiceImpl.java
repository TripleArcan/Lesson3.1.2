package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UsersRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;

    private final RoleRepository roleRepository;


    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UsersRepository usersRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.roleRepository = roleRepository;
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
    @Transactional
    public void updateUser(User user) {
        User existingUser = getUserById(user.getId());

        String userPass = user.getPassword();
        String exPass = existingUser.getPassword();
        if (!userPass.equals(exPass)) {
            String encodedPassword = passwordEncoder.encode(userPass);
            existingUser.setPassword(encodedPassword);
        }

        existingUser.setName(user.getUsername());
        existingUser.setLastName(user.getLastName());
        existingUser.setRoles(user.getRoles());
        usersRepository.save(existingUser);
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
