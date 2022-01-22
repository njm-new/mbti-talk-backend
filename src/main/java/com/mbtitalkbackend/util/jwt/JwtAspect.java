package com.mbtitalkbackend.util.jwt;

import io.jsonwebtoken.*;
import org.apache.ibatis.jdbc.Null;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.boot.json.JsonParseException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Aspect
@Component
public class JwtAspect {
    private static final String SALT = "njm-mbti";

    @Before("@annotation(com.mbtitalkbackend.util.jwt.JwtValidation)")
    public void validate() {
        String tokenHeader = "bearer ";
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        try {
            String token = request.getHeader("Authorization");

            //Validate Authorization Header
            if (!token.toLowerCase().startsWith(tokenHeader)) {
                throw new JwtValidationException();
            }

            token = token.substring(tokenHeader.length());

            Jwts
                    .parser()
                    .setSigningKey(SALT)
                    .parseClaimsJws(token)
                    .getBody();

        } catch (Exception e) {
            throw new JwtValidationException();
        }
    }
}
