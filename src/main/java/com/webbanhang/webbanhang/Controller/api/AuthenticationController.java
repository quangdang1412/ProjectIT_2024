package com.webbanhang.webbanhang.Controller.api;

import com.webbanhang.webbanhang.DTO.response.ResponseData;
import com.webbanhang.webbanhang.DTO.response.ResponseError;
import com.webbanhang.webbanhang.Exception.CustomException;
import com.webbanhang.webbanhang.Exception.ResourceNotFoundException;
import com.webbanhang.webbanhang.Service.IUserService;
import com.webbanhang.webbanhang.Util.CheckLogin;
import jakarta.servlet.http.HttpServlet;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webbanhang.webbanhang.DTO.request.User.AuthenticationRequest;
import com.webbanhang.webbanhang.DTO.request.User.RefreshRequest;
import com.webbanhang.webbanhang.Model.AuthenticationResponse;
import com.webbanhang.webbanhang.Model.RegisterRequest;
import com.webbanhang.webbanhang.Service.Impl.AuthenticationServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Collections;
import java.util.Map;


@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationServiceImpl authenticationService;
    private final CheckLogin checkLogin;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try{
            AuthenticationResponse response = authenticationService.register(registerRequest);
            return ResponseEntity.ok()
                    .header("Authorization", "Bearer " + response.getToken()) // Thêm token vào header
                    .body(response);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/refresh")
    public ResponseData<?> refresh(@RequestHeader("Authorization") String bearerToken) {
        try{
            String token = bearerToken.replace("Bearer ", "");
            RefreshRequest refreshTokenRequest = new RefreshRequest(token);
            AuthenticationResponse response = authenticationService.refreshToken(refreshTokenRequest);
            return new ResponseData<>(HttpStatus.CREATED.value(),"Success",response);
        }
        catch (Exception e){
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request,HttpServletRequest httpServletRequest) {
        try{
            AuthenticationResponse response = authenticationService.login(request);
            HttpSession session = httpServletRequest.getSession();
            session.setAttribute("token", response.getToken());
            session.setAttribute("userdata", response.getUserDto());
            checkLogin.checkLogin(session,response);
            return ResponseEntity.ok()
                    .header("Authorization", "Bearer " + response.getToken()) // Thêm token vào header
                    .body(response);
        }
        catch (ResourceNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token,HttpServletRequest httpServletRequest) {
        // Xóa token trong cơ sở dữ liệu
        boolean success = authenticationService.logout(token.substring(7)); // Loại bỏ "Bearer " trước token
        if (success) {
            HttpSession session = httpServletRequest.getSession(false); // Get the existing session, if any
            if (session != null) {
                session.invalidate(); // Invalidate the session
            }
            return ResponseEntity.ok("Đăng xuất thành công!");
        } else {
            return ResponseEntity.status(400).body("Không tìm thấy token!");
        }
    }
    @GetMapping("/get-token")
    public ResponseEntity<?> getToken(HttpSession session) {
        String token = (String) session.getAttribute("token");
        if (token != null) {
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token not found");
        }
    }
    
}