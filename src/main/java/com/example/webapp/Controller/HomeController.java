package com.example.webapp.Controller;

import com.example.webapp.model.CartItem;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String homePage(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        model.addAttribute("username", username); // Show username in navbar if logged in
        return "Home";
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "About";
    }

    @GetMapping("/cart")
    public String cartPage(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }

        Object cartObj = session.getAttribute("cart");
        List<CartItem> cart;

        if (cartObj instanceof List<?>) {
            // unchecked cast, so we suppress warnings
            @SuppressWarnings("unchecked")
            List<CartItem> tempCart = (List<CartItem>) cartObj;
            cart = tempCart;
        } else {
            cart = new ArrayList<>();
        }
        double grandTotal = 0;
        for (CartItem item : cart) {
            grandTotal += item.getTotal();
        }
        model.addAttribute("username", username);
        model.addAttribute("cart", cart);
        model.addAttribute("grandTotal", grandTotal);

        return "Cart";
    }

    @PostMapping("/cart")
    public String updateCart(
            @RequestParam String name,
            @RequestParam(required = false, defaultValue = "0") int quantity,
            @RequestParam(required = false, defaultValue = "0") double price,
            @RequestParam(required = false, defaultValue = "add") String action,
            HttpSession session) {

        Object cartObj = session.getAttribute("cart");
        List<CartItem> cart;

        if (cartObj instanceof List<?>) {
            @SuppressWarnings("unchecked")
            List<CartItem> tempCart = (List<CartItem>) cartObj;
            cart = tempCart;
        } else {
            cart = new ArrayList<>();
        }

        if ("remove".equalsIgnoreCase(action)) {

            cart.removeIf(item -> item.getName().equals(name));
        } else {

            boolean found = false;
            for (CartItem item : cart) {
                if (item.getName().equals(name)) {
                    item.setQuantity(item.getQuantity() + quantity);
                    found = true;
                    break;
                }
            }
            if (!found && quantity > 0) {
                cart.add(new CartItem(name, quantity, price));
            }
        }

        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

}
