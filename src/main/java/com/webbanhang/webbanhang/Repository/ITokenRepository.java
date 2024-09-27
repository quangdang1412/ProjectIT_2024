package com.webbanhang.webbanhang.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webbanhang.webbanhang.Model.Token;

public interface ITokenRepository extends JpaRepository<Token, Integer> {

    Token findByToken(String refreshToken);

}
