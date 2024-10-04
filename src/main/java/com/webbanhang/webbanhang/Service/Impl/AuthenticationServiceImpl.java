package com.webbanhang.webbanhang.Service.Impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.webbanhang.webbanhang.DTO.request.User.AuthenticationRequest;
import com.webbanhang.webbanhang.DTO.request.User.RefreshRequest;
import com.webbanhang.webbanhang.DTO.request.User.UserRequestDTO;
import com.webbanhang.webbanhang.Exception.InvalidPasswordException;
import com.webbanhang.webbanhang.Mapper.UserMapper;
import com.webbanhang.webbanhang.Model.AuthenticationResponse;
import com.webbanhang.webbanhang.Model.RegisterRequest;
import com.webbanhang.webbanhang.Model.RoleModel;
import com.webbanhang.webbanhang.Model.Token;
import com.webbanhang.webbanhang.Model.UserModel;
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
    private final AuthenticationManager authenticationManager;




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
    public AuthenticationResponse refreshToken(RefreshRequest refreshTokenRequest) {
        var refreshToken = refreshTokenRequest.getToken();
        Token token = tokenRepository.findByToken(refreshToken);
        if (token == null) {
            throw new RuntimeException("Invalid refresh Token");
        }
        if (token.isRevoked()) {
            throw new RuntimeException("Token is revoked");
        }
        if (token.isExpired()) {
            throw new RuntimeException("Token is expired");
        }
        String userId = token.getUserId();
        UserModel user = userRepository.findByUserID(userId);
        String newAccessToken = jwtService.generateToken(user);
        token.setToken(newAccessToken);
        tokenRepository.save(token);
        return AuthenticationResponse.builder()
                .userDto(UserMapper.mapToUserDto(user))
                .token(newAccessToken)
                .build();
    }
    public AuthenticationResponse login(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new InvalidPasswordException("Tài khoản hoặc mật khẩu không đúng!");
        }
        UserModel user = userRepository.getUserByEmail(request.getEmail());
        var jwtToken = jwtService.generateToken(user);
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
}

