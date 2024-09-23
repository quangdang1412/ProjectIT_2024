package com.webbanhang.webbanhang.Service.Impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.webbanhang.webbanhang.Mapper.UserMapper;
import com.webbanhang.webbanhang.Model.AuthenticationResponse;
import com.webbanhang.webbanhang.Model.RegisterRequest;
import com.webbanhang.webbanhang.Model.RoleModel;
import com.webbanhang.webbanhang.Model.Token;
import com.webbanhang.webbanhang.Model.UserModel;
import com.webbanhang.webbanhang.Repository.IRoleRepository;
import com.webbanhang.webbanhang.Repository.ITokenRepository;
import com.webbanhang.webbanhang.Repository.IUserRepository;
import com.webbanhang.webbanhang.Service.IAuthenticationService;
import com.webbanhang.webbanhang.Service.IRoleService;
import com.webbanhang.webbanhang.Service.IUserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {
    private final IUserRepository userRepository;
    private final ITokenRepository tokenRepository; // thêm TokenRepository
    private final JwtServiceImpl jwtService;
    private final IUserService userService;
    private final IRoleService roleService;
    private final PasswordEncoder passwordEncoder;



    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        UserModel newUser = new UserModel();
        String s ="U" + (userService.getAllUser().size()+1);
        newUser.setUserName(request.getName());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setEmail(request.getEmail());
        newUser.setUserID(s);
        RoleModel role = roleService.findRoleByID(2);
        newUser.setRole(role);
        UserModel createdUser = userRepository.save(newUser);
        String jwtToken = jwtService.generateToken(createdUser);
        // lưu token vào database
        Token token = Token.builder()
                .userId(s)
                .token(jwtToken)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
        return AuthenticationResponse.builder()
                .userDto(UserMapper.mapToUserDto(createdUser))
                .token(jwtToken)
                .build();
    }
}

