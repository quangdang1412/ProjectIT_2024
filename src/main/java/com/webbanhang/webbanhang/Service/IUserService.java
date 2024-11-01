package com.webbanhang.webbanhang.Service;

import com.webbanhang.webbanhang.DTO.request.User.UserRequestDTO;
import com.webbanhang.webbanhang.Model.UserCouponModel;
import com.webbanhang.webbanhang.Model.UserModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService {
    UserDetailsService userDetailService();
    List<UserModel> getAllUser();
    UserModel findUserByID(String id);
    UserModel findByEmail(String email);
    List<UserModel> findByRole(int r);
    String changePassword(String id, String pass);
    String deleteUser(String id);
    String saveUser(UserRequestDTO request);
    UserModel updateUser(String id, UserModel user);
    UserModel createUser(UserModel user);
    boolean existsByEmail(String email);
    List<UserCouponModel> findByUserCoupon_UserID(String userId);
}
