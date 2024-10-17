package com.webbanhang.webbanhang.Config.Controller.web;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.webbanhang.webbanhang.Model.UserModel;
import com.webbanhang.webbanhang.Service.IUserService;
import com.webbanhang.webbanhang.Util.CheckLogin;

import jakarta.servlet.http.HttpSession;


@Controller
@RequiredArgsConstructor
public class AccountController {
    @GetMapping("/changePassword")
    public String account(){
        return "web/account";
    }
    @GetMapping("/reset-password")
    public String reset_password(@RequestParam Map<String, String> request,Model model) {
         String email = request.get("email");
         model.addAttribute("resetPassword","resetPassword");
         model.addAttribute("email",email);
         return "web/account";
    }
}
