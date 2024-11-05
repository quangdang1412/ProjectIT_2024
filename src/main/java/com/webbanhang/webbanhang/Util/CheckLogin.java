package com.webbanhang.webbanhang.Util;

import com.webbanhang.webbanhang.Model.AuthenticationResponse;
import com.webbanhang.webbanhang.Model.UserModel;
import com.webbanhang.webbanhang.Service.ICategoryService;
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
    private final ICategoryService categoryService;
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
    public void refreshUser(HttpSession session) {
        if (session.getAttribute("UserLogin") != null) {
            UserModel userModel = (UserModel) session.getAttribute("UserLogin");
            UserModel newUser = userService.findUserByID(userModel.getUserID());
            session.setAttribute("UserLogin", newUser);
            session.setAttribute("countProductInCart", newUser.getUserCart() != null ? newUser.getUserCart().size() : 0);
        }
    }
    public boolean checkRoleAdmin(HttpSession session) {
        return session.getAttribute("UserLoginRole") == null || !session.getAttribute("UserLoginRole").equals("ADMIN");
    }
    public boolean checkRoleSeller(HttpSession session) {
        return session.getAttribute("UserLoginRole") == null || !session.getAttribute("UserLoginRole").equals("SELLER");
    }
}
