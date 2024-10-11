package com.webbanhang.webbanhang.Util;

import com.webbanhang.webbanhang.Model.AuthenticationResponse;
import com.webbanhang.webbanhang.Model.UserModel;
import com.webbanhang.webbanhang.Service.IUserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
@RequiredArgsConstructor
public class CheckLogin {
    private final IUserService userService;
    public void checkLogin(HttpSession session, AuthenticationResponse response )
    {
        if (response != null) {
            UserModel userModel = userService.findByEmail(response.getUserDto().getEmail());
            session.setAttribute("UserLogin", userModel);
            session.setAttribute("UserLoginRole", userModel.getRole().getRoleName());
            session.setAttribute("isLoggedIn", 1);
            session.setAttribute("fullName", userModel.getUsername());
            session.setAttribute("authorities", userModel.getRole().getType());
            session.setAttribute("token",response.getToken());
            session.setAttribute("countProductInCart", userModel.getUserCart() != null ? userModel.getUserCart().size() : 0);
        } else {
            session.setAttribute("isLoggedIn", 0);
        }
    }
}
