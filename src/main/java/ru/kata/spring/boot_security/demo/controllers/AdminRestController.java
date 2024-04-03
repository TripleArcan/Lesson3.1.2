package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("admin")
public class AdminRestController {
    private final UserService userService;

    private final RoleService roleService;

    public AdminRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/allUserInfo")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/userById")
    public User getUserById(@RequestParam("userId") Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/allroles")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/currentuser")
    public ResponseEntity<Map<String, Object>> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("username", username);
        userInfo.put("roles", roles);
        return ResponseEntity.ok(userInfo);
    }

    @PostMapping("/adduser")
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
        userService.updateUser(user);
        return user;
    }

}
