package com.example.webapp.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    @GetMapping("/authorized")
    public String authorized() {
        return "Authorized";
    }
}
