package com.mbtitalkbackend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@Component
public class JsonWebToken {
    private static final String SALT = "njm-mbti";

    public String create(int memberId) {
        //Set issue time
        Date now = new Date();

        //Create JWT
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuedAt(now)
                .setExpiration(new Date((now.getTime() + Duration.ofDays(1).toMillis())))

                .claim("memberId", memberId)
                .signWith(SignatureAlgorithm.HS256, SALT)
                .compact();
    }

    public static Claims validate(String token) {
        String tokenHeader = "Bearer";

        //Validate Authorization Header
        if (token == null || !token.startsWith(tokenHeader)) {
            throw new IllegalStateException();
        }

        //Get token string
        String tokenString = token.substring(tokenHeader.length());

        //Get token Claim
        return Jwts
                .parser()
                .setSigningKey(SALT)
                .parseClaimsJws(tokenString)
                .getBody();
    }
}
