package com.webbanhang.webbanhang.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webbanhang.webbanhang.Model.Token;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITokenRepository extends JpaRepository<Token, Integer> {

    Token findByToken(String refreshToken);

    Optional<Token> findByEmail(String email);

    Token removeTokenByToken(String token);

}
