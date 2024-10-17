package com.webbanhang.webbanhang.Config.Controller.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webbanhang.webbanhang.Model.UserModel;
import com.webbanhang.webbanhang.Service.Impl.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/login/test1")
@RequiredArgsConstructor
public class UserLoginController {
    private final UserServiceImpl userService ;

    @PostMapping
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel user) {
        UserModel createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping
    public ResponseEntity<List<UserModel>> getUsers() {
        List<UserModel> users = userService.getAllUser();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserModel> getUser(@PathVariable String userId) {
        UserModel user = userService.findUserByID(userId);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserModel> updateUser(@PathVariable String userId, @RequestBody UserModel user) {
        UserModel updatedUser = userService.updateUser(userId, user);
        return ResponseEntity.ok(updatedUser);
    }
}
