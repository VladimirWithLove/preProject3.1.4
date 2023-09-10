package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.configs.SuccessUserHandler;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminsController {

    private final UserService userService;
    private final SuccessUserHandler successUserHandler;
    private final UserValidator userValidator;

    @Autowired
    public AdminsController(UserService userService, SuccessUserHandler successUserHandler, UserValidator userValidator) {
        this.userService = userService;
        this.successUserHandler = successUserHandler;
        this.userValidator = userValidator;
    }

    @GetMapping()
    public String showAllUsers(Model model) {
        List<User> allUsers = userService.getAllUsers();
        User user = successUserHandler.getCurrentUser();
        model.addAttribute("allUsers", allUsers);
        model.addAttribute("thisUser", user);
        model.addAttribute("editedUser", new User());
        model.addAttribute("newUser", new User());
        return "forAdmin/all-users";
    }

    @PostMapping(value = "/save")
    public String createUser(@ModelAttribute("newUser") @Valid User user,
                             BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "redirect:/admin";
        }
        userService.add(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/updateUser")
    public String updateUser(@ModelAttribute("editedUser") @Valid User editedUser,
                             BindingResult bindingResult) {
        System.out.println(editedUser.getProfession());
        if (bindingResult.hasErrors()) {
            return "redirect:/admin";
        }
        userService.updateUser(editedUser.getId(), editedUser);

        return "redirect:/admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);

        if (id == successUserHandler.getCurrentUser().getId()) {
            return "redirect:/logout";
        }

        return "redirect:/admin";
    }
}
