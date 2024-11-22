package com.webbanhang.webbanhang.Service;

import com.webbanhang.webbanhang.DTO.request.User.AuthenticationRequest;
import com.webbanhang.webbanhang.DTO.request.User.RefreshRequest;
import com.webbanhang.webbanhang.Model.AuthenticationResponse;
import com.webbanhang.webbanhang.Model.RegisterRequest;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface IAuthenticationService {
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse login(AuthenticationRequest request, String typeLogin);

    AuthenticationResponse refreshToken(RefreshRequest request);

    boolean logout(String token);

    String forgotPassword(String email, String randomString) throws MessagingException, UnsupportedEncodingException;
}
    
