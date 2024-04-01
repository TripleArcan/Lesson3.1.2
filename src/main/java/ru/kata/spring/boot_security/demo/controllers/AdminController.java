package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("admin")
public class AdminController {

    private final RoleService roleService;
    private final UserService userService;

    public AdminController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/adminJSON")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/userByIdJSON")
    public User getUserById(@RequestParam("userId") Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/adminpage")
    public ModelAndView printAllUsers() {
        ModelAndView mav = new ModelAndView("admin");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<String> authenticationRoles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        mav.addObject("username", username);
        mav.addObject("authenticationRoles", authenticationRoles);
        return mav;
    }

    @PostMapping("/adduser")
    @ResponseBody
    public ResponseEntity<String> addUser(@RequestBody User user) {
        userService.addUser(user);
        return ResponseEntity.ok("Пользователь успешно добавлен");
    }

    @DeleteMapping("/delete")
    public void deleteUser(@RequestParam("userName") String userName) {
        userService.deleteUser(userName);
    }

    @PutMapping("/edit")
    public User updateUser(@RequestBody User user) {
        User existingUser = userService.getUserById(user.getId());

        String userPass = user.getPassword();
        String exPass = existingUser.getPassword();
        if (!userPass.equals(exPass)) {
            String encodedPassword = userService.setPasswordEncoder(user.getPassword());
            existingUser.setPassword(encodedPassword);
        }

        existingUser.setName(user.getUsername());
        existingUser.setLastName(user.getLastName());
        existingUser.setRoles(user.getRoles());

        userService.updateUser(existingUser);
        return user;
    }

}
