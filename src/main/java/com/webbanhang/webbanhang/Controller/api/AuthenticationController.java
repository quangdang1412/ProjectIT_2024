package com.webbanhang.webbanhang.Controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import com.webbanhang.webbanhang.DTO.request.User.AuthenticationRequest;
import com.webbanhang.webbanhang.DTO.request.User.RefreshRequest;
import com.webbanhang.webbanhang.Exception.ErrorResponse;
import com.webbanhang.webbanhang.Model.AuthenticationResponse;
import com.webbanhang.webbanhang.Model.RegisterRequest;
import com.webbanhang.webbanhang.Model.UserModel;
import com.webbanhang.webbanhang.Repository.ITokenRepository;
import com.webbanhang.webbanhang.Service.ITokenService;
import com.webbanhang.webbanhang.Service.Impl.AuthenticationServiceImpl;
import com.webbanhang.webbanhang.Service.Impl.UserServiceImpl;

import ch.qos.logback.core.subst.Token;

import org.springframework.security.core.annotation.AuthenticationPrincipal;



@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationServiceImpl authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) {
        AuthenticationResponse response = authenticationService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh(@RequestBody RefreshRequest refreshTokenRequest) {
        AuthenticationResponse response = authenticationService.refreshToken(refreshTokenRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
            AuthenticationResponse response = authenticationService.login(request);
            
            return ResponseEntity.ok()
            .header("Authorization", "Bearer " + response.getToken()) // Thêm token vào header
            .body(response);
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        // Xóa token trong cơ sở dữ liệu
        boolean success = authenticationService.logout(token.substring(7)); // Loại bỏ "Bearer " trước token
        if (success) {
            return ResponseEntity.ok("Đăng xuất thành công!");
        } else {
            return ResponseEntity.status(400).body("Không tìm thấy token!");
        }
    }
    
}