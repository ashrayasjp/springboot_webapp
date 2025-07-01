package com.example.webapp.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @RequestMapping("/")
    public String log() {
        return "<button onclick=\"window.location.href='/login'\">Login</button> &nbsp; &nbsp; <button onclick=\"window.location.href='/signup'\">Signup</button>";

    }

}
