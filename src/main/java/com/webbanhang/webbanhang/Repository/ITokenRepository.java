package com.webbanhang.webbanhang.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webbanhang.webbanhang.Model.Token;

import java.util.Optional;

public interface ITokenRepository extends JpaRepository<Token, Integer> {

    Token findByToken(String refreshToken);
    Optional<Token> findByEmail(String email);
    Token  removeTokenByToken(String token);

}
