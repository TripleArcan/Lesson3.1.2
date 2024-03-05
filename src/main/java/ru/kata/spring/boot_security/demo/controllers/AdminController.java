package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UsersRepository;
import ru.kata.spring.boot_security.demo.servise.UserService;

import java.util.List;


@Controller
public class AdminController {

    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;
    private final UserService userService;

    public AdminController(UsersRepository usersRepository, RoleRepository roleRepository, UserService userService) {
        this.usersRepository = usersRepository;
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @GetMapping(value = "/allusers")
    public String printAllUsers(ModelMap modelMap) {
        modelMap.addAttribute("users", userService.getAllUsers());
        return "allusers";
    }

    @GetMapping(value = "/adduser")
    public ModelAndView newUser() {
        User user = new User();
        ModelAndView mav = new ModelAndView("adduser");
        mav.addObject("user", user);

        List<Role> roles = roleRepository.findAll();

        mav.addObject("allRoles", roles);

        return mav;
    }

    @PostMapping(value = "/adduser")
    public String addUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/allusers";
    }

    @GetMapping("/deleteuser")
    public String showDeleteUser() {
        return "delete";
    }

    @PostMapping("/deleteuser")
    public String DeleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/allusers";
    }

    @GetMapping("/edituser")
    public ModelAndView showUpdateUserForm(@RequestParam("id") Long id) {
        User user = userService.getUserById(id);
        ModelAndView mav = new ModelAndView("edit");
        mav.addObject("user", user);

        List<Role> roles = roleRepository.findAll();

        mav.addObject("allRoles", roles);

        return mav;
    }

    //@GetMapping("/edituser")
    //public String showUpdateUserForm(@RequestParam("id") Long id, Model model) {
    //    User user = userService.getUserById(id);
    //    model.addAttribute("user", user);
    //    return "edit";
    //}

    @PostMapping("/edituser")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/allusers";
    }

}
