package org.example.test1.controllers;

import org.example.test1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("")
public class firstController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String hello() {
        return "hello";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/{username}")
    public String show(@PathVariable("username") String username, Model model) {
        model.addAttribute("user", userService.findByLogin(username));
        return "user/show";
    }

}
