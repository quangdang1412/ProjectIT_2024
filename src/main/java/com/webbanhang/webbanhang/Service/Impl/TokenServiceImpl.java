package com.webbanhang.webbanhang.Service.Impl;

import com.webbanhang.webbanhang.Model.Token;
import org.springframework.stereotype.Service;

import com.webbanhang.webbanhang.Repository.ITokenRepository;
import com.webbanhang.webbanhang.Service.ITokenService;

import lombok.*;

import java.util.Optional;


@Service
public record TokenServiceImpl(ITokenRepository tokenRepository){
    public void save(Token token) {
        Optional<Token> tokenOptional = tokenRepository.findByEmail(token.getEmail());
        if (tokenOptional.isEmpty()) {
            tokenRepository.save(token);
        } else {
            Token currentToken = tokenOptional.get();
            currentToken.setToken(token.getToken());
            tokenRepository.save(currentToken);
        }
    }
    public boolean removeToken(Token token) {
        tokenRepository.delete(token);
        return true;
    }
    
}
