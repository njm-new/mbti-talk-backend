package com.mbtitalkbackend.util.jwt;

import io.jsonwebtoken.*;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
public class JwtAspect {
    @Before("@annotation(com.mbtitalkbackend.util.jwt.JwtValidation)")
    public void validate() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        String tokenHeader = "bearer ";
        String token = request.getHeader("Authorization");

        try {

            //Validate Authorization Header
            if (!token.toLowerCase().startsWith(tokenHeader)) {
                throw new JwtValidationException();
            }

            token = token.substring(tokenHeader.length());

            Jwts
                    .parser()
                    .setSigningKey(JsonWebToken.SALT)
                    .parseClaimsJws(token)
                    .getBody();

        } catch (Exception e) {
            throw new JwtValidationException();
        }
    }
}
