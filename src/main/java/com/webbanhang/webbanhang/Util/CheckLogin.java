package com.webbanhang.webbanhang.Util;

import com.webbanhang.webbanhang.Model.UserModel;
import com.webbanhang.webbanhang.Service.IUserService;
import com.webbanhang.webbanhang.security.UserLogin;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class CheckLogin {
    public void checkLogin(HttpSession session, Model model,IUserService userService)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated() && !(auth.getPrincipal() instanceof String)) {
            UserLogin user = (UserLogin) auth.getPrincipal();
            UserModel userModel = userService.findByEmail(user.getEmail());
            session.setAttribute("UserLogin", userModel);
            session.setAttribute("UserLoginRole",userModel.getRole().getRoleName());
            session.setAttribute("isLogIn",true);
            session.setAttribute("fullName",user.getFullName());
            if (userModel.getUserCart() != null)
                session.setAttribute("countProductInCart",userModel.getUserCart().size());
            else
                session.setAttribute("countProductInCart",0);
            model.addAttribute("fullName", user.getFullName());
            model.addAttribute("authorities", user.getAuthorities());
            model.addAttribute("isLoggedIn", true);

        } else {
            session.setAttribute("isLogIn",false);
            model.addAttribute("isLoggedIn", false);
        }
    }
}
