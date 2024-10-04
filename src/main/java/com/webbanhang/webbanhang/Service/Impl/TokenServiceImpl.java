package com.webbanhang.webbanhang.Service.Impl;

import org.springframework.stereotype.Service;

import com.webbanhang.webbanhang.Repository.ITokenRepository;
import com.webbanhang.webbanhang.Service.ITokenService;

import lombok.*;


@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements ITokenService {
    private final ITokenRepository tokenRepository;

    @Override
    public boolean removeToken(String token) {
        tokenRepository.removeTokenByToken(token);
        return true;
    }
    
}
