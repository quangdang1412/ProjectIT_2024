package com.webbanhang.webbanhang.Service;

import com.webbanhang.webbanhang.DTO.request.User.AuthenticationRequest;
import com.webbanhang.webbanhang.DTO.request.User.RefreshRequest;
import com.webbanhang.webbanhang.Model.AuthenticationResponse;
import com.webbanhang.webbanhang.Model.RegisterRequest;

public interface IAuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse login(AuthenticationRequest request);
    AuthenticationResponse refreshToken(RefreshRequest request);
    boolean logout(String token);

    String forgotPassword(String email);

    String resetPassword(String key);
}
    
