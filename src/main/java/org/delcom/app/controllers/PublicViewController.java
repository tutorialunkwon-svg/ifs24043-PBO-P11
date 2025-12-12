package org.delcom.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicViewController {

    @GetMapping("/")
    public String home() {
        return "pages/public-home";
    }

    @GetMapping("/login")
    public String login() {
        return "pages/auth/login";
    }

    @GetMapping("/register")
    public String register() {
        return "pages/auth/register";
    }
}
