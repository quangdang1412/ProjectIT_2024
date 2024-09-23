package com.webbanhang.webbanhang.Service;

import com.webbanhang.webbanhang.Model.UserModel;

public interface IJwtService {
    String generateToken(UserModel user);
    String extractUsername(String token);

}
