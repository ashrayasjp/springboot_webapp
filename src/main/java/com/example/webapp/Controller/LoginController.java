package com.example.webapp.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "Login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
            @RequestParam String password,
            RedirectAttributes redirectAttributes,
            Model model) {

        if ("root".equals(username) && "password".equals(password)) {
            redirectAttributes.addFlashAttribute("username", username);
            return "redirect:/Authorized";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "Login";
        }
    }

    @GetMapping("/Authorized")
    public String authorizedPage() {
        return "Authorized";
    }
}
