package com.webbanhang.webbanhang.Controller.admin;

import com.webbanhang.webbanhang.Model.UserModel;
import com.webbanhang.webbanhang.Repository.IUserRepository;
import com.webbanhang.webbanhang.Service.IRoleService;
import com.webbanhang.webbanhang.Service.IUserService;
import com.webbanhang.webbanhang.Util.CheckLogin;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(value = {"/admin"})
@RequiredArgsConstructor
public class UserController {

    private final IUserRepository userRepository;
    private final IUserService userService;
    private final IRoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final CheckLogin checkLogin;

    @GetMapping("/User")
    public String checkActionGet(Model model, @RequestParam Map<String, String> allParams, HttpSession session) {
        if (checkLogin.checkRoleAdmin(session)) {
            return "redirect:/404";
        }
        String action = allParams.get("action");
        String id = allParams.get("id");
        if (action == null) {
            action = "list";
        }
        switch (action) {
            case "new":
                return addUserForm(model);
            case "edit":
                return updateUserForm(model, id);
            default:
                return listUser(model);

        }
    }

    public String listUser(Model model) {
        model.addAttribute("listUser", userRepository.findAll());
        return "/admin/User/admin-user";
    }

    public String addUserForm(Model model) {
        model.addAttribute("listRole", roleService.getAllRole());
        model.addAttribute("checkUser", null);
        String s = "U" + UUID.randomUUID().toString().substring(0, 8);
        model.addAttribute("UserID", s);
        model.addAttribute("User", new UserModel());
        return "/admin/User/AddUser";
    }

    public String updateUserForm(Model model, String id) {
        UserModel a = userService.findUserByID(id);
        model.addAttribute("listRole", roleService.getAllRole());
        model.addAttribute("checkUser", "update");
        model.addAttribute("UserID", a.getUserID());
        model.addAttribute("UserName", a.getUsername());
        model.addAttribute("Phone", a.getPhone());
        model.addAttribute("Email", a.getEmail());
        model.addAttribute("Password", passwordEncoder.encode(a.getPassword()));
        model.addAttribute("Address", a.getAddress());
        model.addAttribute("Gender", a.getGender());
        model.addAttribute("RoleName", a.getRole().getRoleName());
        model.addAttribute("User", a);
        return "/admin/User/AddUser";
    }
}