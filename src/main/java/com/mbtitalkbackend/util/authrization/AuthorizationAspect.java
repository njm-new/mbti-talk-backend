package com.mbtitalkbackend.util.authrization;

import io.jsonwebtoken.*;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
public class AuthorizationAspect {
    @Before("@annotation(com.mbtitalkbackend.util.authrization.Authorization)")
    public void validate() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        String tokenHeader = "bearer ";
        String token = request.getHeader("Authorization");
        try {
            //Validate Authorization Header
            if (!token.toLowerCase().startsWith(tokenHeader)) {
                throw new AuthorizationException();
            }

            token = token.substring(tokenHeader.length());

            Jwts
                    .parser()
                    .setSigningKey(AccessToken.SALT)
                    .parseClaimsJws(token)
                    .getBody();

        } catch (ExpiredJwtException e) {
            throw new ExpiredAccessTokenException();
        } catch (Exception e) {
            throw new AuthorizationException();
        }
    }
}
