package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final RoleService roleService;
    private final UserService userService;

    public AdminController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/allusers")
    public String printAllUsers(ModelMap modelMap) {
        modelMap.addAttribute("users", userService.getAllUsers());
        return "allusers";
    }

    @GetMapping("/adduser")
    public ModelAndView newUser() {
        User user = new User();
        ModelAndView mav = new ModelAndView("adduser");
        mav.addObject("user", user);

        List<Role> roles = roleService.getAllRoles();

        mav.addObject("allRoles", roles);

        return mav;
    }

    @PostMapping("/adduser")
    public String addUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/admin/allusers";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("userName") String userName) {
        userService.deleteUser(userName);
        return "redirect:/admin/allusers";
    }

    @GetMapping("/edit")
    public ModelAndView showUpdateUserForm(@RequestParam("userName") String name) {
        User user = userService.getUserByUsername(name);
        ModelAndView mav = new ModelAndView("edit");
        mav.addObject("user", user);

        List<Role> roles = roleService.getAllRoles();

        mav.addObject("allRoles", roles);

        return mav;
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin/allusers";
    }

}
