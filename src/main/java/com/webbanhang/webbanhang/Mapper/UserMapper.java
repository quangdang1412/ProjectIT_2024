package com.webbanhang.webbanhang.Mapper;

import com.webbanhang.webbanhang.DTO.request.User.UserRequestDTO;
import com.webbanhang.webbanhang.Model.UserModel;

public class UserMapper {

    public static UserRequestDTO mapToUserDto(UserModel user) {
        UserRequestDTO userDto = new UserRequestDTO();
        userDto.setUserID(user.getUserID());
        userDto.setPassword(user.getPassword());
       
        userDto.setUserName(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setType(user.getRole().getType());
        return userDto;
    }
}