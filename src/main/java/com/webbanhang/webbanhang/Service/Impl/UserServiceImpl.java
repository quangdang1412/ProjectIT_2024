package com.webbanhang.webbanhang.Service.Impl;

import com.webbanhang.webbanhang.DTO.request.User.UserRequestDTO;
import com.webbanhang.webbanhang.Exception.CustomException;
import com.webbanhang.webbanhang.Exception.ResourceNotFoundException;
import com.webbanhang.webbanhang.Model.RoleModel;
import com.webbanhang.webbanhang.Model.UserCouponModel;
import com.webbanhang.webbanhang.Model.UserModel;
import com.webbanhang.webbanhang.Repository.IRoleRepository;
import com.webbanhang.webbanhang.Repository.IUserCouponRepository;
import com.webbanhang.webbanhang.Repository.IUserRepository;
import com.webbanhang.webbanhang.Service.IUserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final IUserCouponRepository userCouponRepository;

    @Override
    public UserDetailsService userDetailService() {
        return email -> userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Transactional
    @Override
    public List<UserModel> getAllUser() {
        return (List<UserModel>) userRepository.findAll();
    }

    @Override
    public UserModel findUserByID(String id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public UserModel findByEmail(String email) {
        UserModel user = userRepository.getUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        return user;
    }

    @Override
    public List<UserModel> findByRole(int r) {
        return userRepository.getAllUserByType(r);
    }

    @Override
    public String changePassword(String id, String pass) {
        try{
            UserModel userModel = findUserByID(id);
            userModel.setPassword(pass);
            userRepository.save(userModel);
            return id;
        }catch (Exception e){
            String error = e.getMessage();
            String property = error.substring(error.lastIndexOf(".")+1,error.lastIndexOf("]"));
            log.info(error);
            throw new CustomException(property+ " has been used");
        }
    }

    @Override
    public String deleteUser(String id) {
        try{
            UserModel userModel = findUserByID(id);
            userModel.setActive(!userModel.isActive());
            userRepository.save(userModel);
            return id;
        }catch (Exception e){
            String error = e.getMessage();
            String property = error.substring(error.lastIndexOf(".")+1,error.lastIndexOf("]"));
            log.info(error);
            throw new CustomException(property+ " has been used");
        }
    }
    @Override
    public String saveUser(UserRequestDTO request,int check) {
        RoleModel role = roleRepository.findById(request.getType()).orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        try{
            UserModel userModel= UserModel.builder()
                    .userID(request.getUserID())
                    .userName(request.getUserName())
                    .role(role)
                    .email(request.getEmail())
                    .phone(request.getPhone())
                    .address(request.getAddress())
                    .gender(String.valueOf(request.getGender()))
                    .build();
            if(check ==1){
                userModel.setPassword(passwordEncoder.encode(request.getPassword()));
            }
            else{
                userModel.setPassword(userRepository.getUserByEmail(request.getEmail()).getPassword());
            }
            userRepository.save(userModel);
            return userModel.getUserID();
        }catch (Exception e){
            String error = e.getMessage();
            String property = error.substring(error.lastIndexOf(".")+1,error.lastIndexOf("]"));
            log.info(error);
            throw new CustomException(property+ " has been used");
        }
    }
    @Override
    public UserModel updateUser(String userId, UserModel user) {
        UserModel existingUser = userRepository.findById(userId).orElse(null);
        if (existingUser != null) {
            existingUser.setEmail(user.getEmail());
            existingUser.setUserName(user.getUsername());
            existingUser.setPhone(user.getPhone());
            existingUser.setAddress(user.getAddress());
            existingUser.setGender(user.getGender());
            return userRepository.save(existingUser);
        }
        return null;
    }
    @Override
    public UserModel createUser(UserModel user) {
        return userRepository.save(user);
    }
    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public List<UserCouponModel> findByUserCoupon_UserID(String userId) {
        return userCouponRepository.findByUserCoupon_UserID(userId);
    }


}
