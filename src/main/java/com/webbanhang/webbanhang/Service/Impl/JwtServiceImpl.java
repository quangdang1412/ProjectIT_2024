package com.webbanhang.webbanhang.Service.Impl;

import java.security.Key;
import java.util.Date;

import com.webbanhang.webbanhang.Model.Token;
import com.webbanhang.webbanhang.Repository.ITokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.webbanhang.webbanhang.Model.UserModel;
import com.webbanhang.webbanhang.Service.IJwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import java.time.Instant;
import java.util.Optional;
import java.util.function.Function;

import io.jsonwebtoken.security.Keys;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements IJwtService {
    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    private final ITokenRepository tokenRepository;
    @Override
    public String extractUsername(String token) {
        Claims claims = extractClaims(token);
        if (claims != null) {
            Date expirationTime = claims.getExpiration();
            boolean isExpired = expirationTime.before(Date.from(Instant.now()));
            if (!isExpired) {
                return claims.getSubject();
            } else return null;
        }
        return null;
    }

    @Override
    public String generateToken(UserModel user) {
        return Jwts
                .builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public boolean isValid(String token, UserModel user) {
        final String email = extractUsername(token);
        Optional<Token> tokenOptional = tokenRepository.findByEmail(email);
        boolean checkToken = tokenOptional.get().getToken().equals(token);
        return (email.equals(user.getEmail()) && !isTokenExpired(token) && checkToken);
    }
    public boolean isTokenExpired(String token) {
        return  extractExpiration(token).before(new Date());
    }
    private <T> T extractClaim(String token, Function<Claims,T> claimResolver){
        final Claims claims = extractClaims(token);
        return claimResolver.apply(claims);
    }
    private Date extractExpiration(String token) {
        return  extractClaim(token, Claims::getExpiration);
    }
}
