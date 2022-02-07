package com.mbtitalkbackend.util.authrization;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Duration;
import java.util.Date;

public class AccessTokenManager {
    public static final String SALT = "njm-mbti";

    public String create(String memberId) {
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

    public Claims validate(String token) {
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
