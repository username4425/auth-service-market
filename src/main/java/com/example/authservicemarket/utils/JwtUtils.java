package com.example.authservicemarket.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.example.authservicemarket.entry.MarketUser;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Log4j2
public class JwtUtils {
    private static final String issuer = "auth app";


    private long expirationTimeMillis;
    private Algorithm algorithm;
    private JWTVerifier verifier;

    public JwtUtils(
            @Value("${jwt-auth.token-expiration-time}") long hours,
            @Value("${jwt-auth.secret}") String secret){
        algorithm = Algorithm.HMAC512(secret);
        expirationTimeMillis = hours * 60 * 60 * 1000;
        verifier = JWT.require(algorithm).withIssuer(issuer).build();
    }

    public String generateToken(MarketUser user){
        return JWT.create()
                .withSubject(user.getId())
                .withIssuer(issuer)
                .withClaim("authorities", user.getAuthorities())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusMillis(expirationTimeMillis))
                .sign(algorithm);
    }

    public boolean isValid(String token){
        try{
            verifier.verify(token);
            return true;
        }catch(JWTDecodeException e){
            log.warn(e.getMessage());
            return false;
        }
    }
}
