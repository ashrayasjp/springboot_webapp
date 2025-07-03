package com.example.webapp.Controller;

import com.example.webapp.repository.UserRepository;
import com.example.webapp.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/signup")
    public String Signup() {
        return "Signup";
    }

    @PostMapping("/signup")
    public String SignupHandle(@ModelAttribute User user, Model model) {
        if (userRepository.existsByEmail(user.getEmail())) {
            model.addAttribute("error", "Email already exists");
            return "Signup";

        }
        if (userRepository.existsByUsername(user.getUsername())) {
            model.addAttribute("error", "Username already exists");
            return "Signup";
        }
        userRepository.save(user);
        return "redirect:/cart";

    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "Login";
    }

    @PostMapping("/login")
    public String handleLogin(@ModelAttribute User user, Model model, HttpSession session) {
        User dbUser = userRepository.findByUsername(user.getUsername());
        if (dbUser == null || !dbUser.getPassword().equals(user.getPassword())) {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }

        session.setAttribute("username", dbUser.getUsername());
        return "redirect:/cart";
    }

    @GetMapping("/authorized")
    public String authorizedPage() {

        return "Authorized";
    }

    @GetMapping("/cart")
    public String secretPage(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        model.addAttribute("username", username);
        return "Cart";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/home";

    }

}
