package com.webbanhang.webbanhang.Service.Impl;

import com.webbanhang.webbanhang.Constraint.JobQueue;
import com.webbanhang.webbanhang.DTO.request.EmailRequest;
import com.webbanhang.webbanhang.DTO.request.ResetPasswordRequest;
import com.webbanhang.webbanhang.Exception.ResourceNotFoundException;
import com.webbanhang.webbanhang.Model.*;
import com.webbanhang.webbanhang.Service.ITokenRedisService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.token.TokenService;
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

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {
    private final IUserRepository userRepository;
    private final TokenServiceImpl tokenService;
    private final JwtServiceImpl jwtService;
    private final IUserService userService;
    private final IRoleService roleService;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final ITokenRedisService tokenRedisService;
    private final RabbitTemplate rabbitTemplate;


    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        if (userService.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email đã đăng kí tài khoản: " + request.getEmail());
        }
        if (request.getPassword().length() < 6) {
            throw new EmailAlreadyExistsException("Mật khẩu phải dài hơn hoặc bằng 6 kí tự");
        }
        UserModel newUser = new UserModel();
        String userId = "U" + UUID.randomUUID().toString().substring(0, 8);
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
                .email(createdUser.getEmail())
                .token(jwtToken)
                .expired(false)
                .revoked(false)
                .build();
        tokenService.save(token);

        return AuthenticationResponse.builder()
                .userDto(UserMapper.mapToUserDto(createdUser))
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        final String email = jwtService.extractUsername(refreshToken);
        Token token = tokenRedisService.getToken(email);
        if (token == null)
            tokenService.tokenRepository().findByEmail(email).get();

        if (token == null || token.isRevoked() || token.isExpired()) {
            throw new TokenException("Invalid or expired refresh token");
        }
        UserModel user = userService.findByEmail(token.getEmail());
        String newAccessToken = jwtService.generateToken(user);
        token.setToken(newAccessToken);
        token.setExpired(false);
        token.setRevoked(false);
        tokenService.save(token);
        tokenRedisService.addTokenRedis(user.getEmail(), token);
        return AuthenticationResponse.builder()
                .userDto(UserMapper.mapToUserDto(user))
                .token(newAccessToken)
                .build();
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest request, String typeLogin) {
        if (typeLogin.equals("normal")) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        }
        UserModel user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new ResourceNotFoundException("Tài khoản hoặc mật khẩu không chính xác"));
//        if (request.getPassword().user.getPassword())) {
//            throw new ResourceNotFoundException("Tài khoản hoặc mật khẩu không chính xác");
//        }
        String jwtToken = jwtService.generateToken(user);
        Token token = Token.builder()
                .email(user.getEmail())
                .token(jwtToken)
                .expired(false)
                .revoked(false)
                .build();
        tokenService.save(token);
        tokenRedisService.addTokenRedis(user.getEmail(), token);
        UserRequestDTO userDto = UserMapper.mapToUserDto(user);
        return AuthenticationResponse.builder()
                .userDto(userDto)
                .token(jwtToken)
                .build();
    }

    @Override
    public boolean logout(String token) {
        final String email = jwtService.extractUsername(token);
        Token tokenEntity = tokenService.tokenRepository().findByEmail(email).get();

        if (tokenEntity == null) {
            return false;
        }
        tokenEntity.setRevoked(true);
        tokenRedisService.deleteTokenRedis(email);
        return tokenService.removeToken(tokenEntity);
    }

    @Override
    public String forgotPassword(String email, String randomString) throws MessagingException, UnsupportedEncodingException {
        try {
            if (userService.existsByEmail(email)) {
                String confirmLink = "http://localhost:8080/reset-password?email=" + email + "&resetPasswordKey=" + randomString;
                rabbitTemplate.convertAndSend(JobQueue.QUEUE_SEND_EMAIL_RESET_PASS, new ResetPasswordRequest(confirmLink, email));
//                mailService.sendResetPasswordMail(confirmLink, email);
                return "Success";
            } else
                return "Failed";
        } catch (Exception e) {
            return "Failed";
        }
    }
}


