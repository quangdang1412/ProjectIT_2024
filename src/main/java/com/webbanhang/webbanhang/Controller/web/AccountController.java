package com.webbanhang.webbanhang.Controller.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@Controller
@RequiredArgsConstructor
public class AccountController {
    @GetMapping("/changePassword")
    public String account() {
        return "web/account";
    }

    @GetMapping("/reset-password")
    public String reset_password(@RequestParam Map<String, String> request, Model model) {
        String email = request.get("email");
        model.addAttribute("resetPassword", "resetPassword");
        model.addAttribute("email", email);
        return "web/account";
    }
}
