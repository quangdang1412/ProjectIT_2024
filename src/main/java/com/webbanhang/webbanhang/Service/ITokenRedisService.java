package com.webbanhang.webbanhang.Service;

import com.webbanhang.webbanhang.Model.AuthenticationResponse;
import com.webbanhang.webbanhang.Model.Token;
import com.webbanhang.webbanhang.Model.UserModel;

public interface ITokenRedisService {

    Token getToken(String email);

    void addTokenRedis(String email, Token token);

    void deleteTokenRedis(String email);

}
