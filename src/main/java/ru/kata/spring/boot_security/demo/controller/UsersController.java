package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.configs.SuccessUserHandler;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/user")
public class UsersController {
    private final SuccessUserHandler successUserHandler;

    @Autowired
    public UsersController(SuccessUserHandler successUserHandler) {
        this.successUserHandler = successUserHandler;
    }

    @GetMapping()
    public String showUserInfo(Model model) {
        User user = successUserHandler.getCurrentUser();
        model.addAttribute("user", user);

        return "/forUser/user";
    }
}
