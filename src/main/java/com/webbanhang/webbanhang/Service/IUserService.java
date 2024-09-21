package com.webbanhang.webbanhang.Service;

import com.webbanhang.webbanhang.DTO.request.User.UserRequestDTO;
import com.webbanhang.webbanhang.Model.UserModel;

import java.util.List;

public interface IUserService {
    List<UserModel> getAllUser();
    UserModel findUserByID(String id);
    UserModel findByEmail(String email);
    List<UserModel> findByRole(int r);
    String changePassword(String id, String pass);
    String deleteUser(String id);
    String saveUser(UserRequestDTO request);


}
