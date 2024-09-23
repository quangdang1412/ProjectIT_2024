package com.webbanhang.webbanhang.Service;

import com.webbanhang.webbanhang.Model.AuthenticationResponse;
import com.webbanhang.webbanhang.Model.RegisterRequest;

public interface IAuthenticationService {
    AuthenticationResponse register(RegisterRequest request);

    
}
    
