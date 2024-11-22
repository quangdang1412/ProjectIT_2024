package com.webbanhang.webbanhang.Model;

import com.webbanhang.webbanhang.DTO.request.User.UserRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
    private UserRequestDTO userDto;
}