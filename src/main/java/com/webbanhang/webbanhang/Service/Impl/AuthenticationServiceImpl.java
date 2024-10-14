package com.webbanhang.webbanhang.Service.Impl;

import com.webbanhang.webbanhang.Exception.ResourceNotFoundException;
import com.webbanhang.webbanhang.Model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.webbanhang.webbanhang.DTO.request.User.AuthenticationRequest;
import com.webbanhang.webbanhang.DTO.request.User.RefreshRequest;
import com.webbanhang.webbanhang.DTO.request.User.UserRequestDTO;
import com.webbanhang.webbanhang.Exception.EmailAlreadyExistsException;
import com.webbanhang.webbanhang.Exception.InvalidPasswordException;
import com.webbanhang.webbanhang.Exception.TokenException;
import com.webbanhang.webbanhang.Mapper.UserMapper;
import com.webbanhang.webbanhang.Repository.ITokenRepository;
import com.webbanhang.webbanhang.Repository.IUserRepository;
import com.webbanhang.webbanhang.Service.IAuthenticationService;
import com.webbanhang.webbanhang.Service.IRoleService;
import com.webbanhang.webbanhang.Service.IUserService;

import lombok.RequiredArgsConstructor;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {
    private final IUserRepository userRepository;
    private final ITokenRepository tokenRepository;
    private final JwtServiceImpl jwtService;
    private final IUserService userService;
    private final IRoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        if (userService.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email đã đăng kí tài khoản: " + request.getEmail());
        }
        UserModel newUser = new UserModel();
        String userId = "U" + (userService.getAllUser().size() + 1);
        newUser.setUserName(request.getName());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setEmail(request.getEmail());
        newUser.setPhone(request.getPhone());
        newUser.setUserID(userId);
        RoleModel role = roleService.findRoleByID(2);
        newUser.setRole(role);
        

        UserModel createdUser = userRepository.save(newUser);
        String jwtToken = jwtService.generateToken(createdUser);

        Token token = Token.builder()
                .userId(userId)
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

    @Override
    public AuthenticationResponse refreshToken(RefreshRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        Token token = tokenRepository.findByToken(refreshToken);
        
        if (token == null || token.isRevoked() || token.isExpired()) {
            throw new TokenException("Invalid or expired refresh token");
        }
        UserModel user = userRepository.findByUserID(token.getUserId());
        String newAccessToken = jwtService.generateToken(user);
        token.setToken(newAccessToken);
        token.setExpired(false);
        token.setRevoked(false);
        tokenRepository.save(token);
        return AuthenticationResponse.builder()
                .userDto(UserMapper.mapToUserDto(user))
                .token(newAccessToken)
                .build();
    }
    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        UserModel user = userRepository.findByEmail(request.getEmail()).orElseThrow(()-> new ResourceNotFoundException("Tài khoản hoặc mật khẩu không chính xác"));
        String jwtToken = jwtService.generateToken(user);
        Token token = Token.builder()
                .userId(user.getUserID())
                .token(jwtToken)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
        UserRequestDTO userDto = UserMapper.mapToUserDto(user);
        return AuthenticationResponse.builder()
                .userDto(userDto)
                .token(jwtToken)
                .build();
    }

    @Override
    public boolean logout(String token) {
        Token tokenEntity = tokenRepository.findByToken(token);
        if (tokenEntity == null) {
            return false;
        }
        tokenEntity.setRevoked(true);
        tokenRepository.delete(tokenEntity);
        return true;
    }
}


