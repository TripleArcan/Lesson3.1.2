package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.security.UsersDetails;

@Controller
public class UserController {
    @GetMapping("/user")
    public String getUserInfo(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsersDetails usersDetails = (UsersDetails) authentication.getPrincipal();
        User user = usersDetails.getUser();
        model.addAttribute("user", user);
        return "userinfo";
    }
}
