package com.example.webapp.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String log() {
        return "Home";

    }

    @GetMapping("/about")
    public String AboutPage() {
        return "About";
    }

}
