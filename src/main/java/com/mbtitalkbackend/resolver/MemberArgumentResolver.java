package com.mbtitalkbackend.resolver;

import com.mbtitalkbackend.member.model.vo.Member;
import com.mbtitalkbackend.member.repository.MemberRepository;
import com.mbtitalkbackend.util.authrization.AccessToken;
import com.mbtitalkbackend.util.authrization.AuthorizationException;
import com.mbtitalkbackend.util.authrization.ExpiredAccessTokenException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class MemberArgumentResolver implements HandlerMethodArgumentResolver {
    private final MemberRepository memberRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Member.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        String tokenHeader = "bearer ";
        String token = request.getHeader("Authorization");
        int memberId;

        try {
            //Validate Authorization Header
            if (!token.toLowerCase().startsWith(tokenHeader)) {
                throw new AuthorizationException();
            }

            token = token.substring(tokenHeader.length());

            memberId = (int) Jwts
                    .parser()
                    .setSigningKey(AccessToken.SALT)
                    .parseClaimsJws(token)
                    .getBody()
                    .get("memberId");

        } catch (ExpiredJwtException e) {
            throw new ExpiredAccessTokenException();
        } catch (Exception e) {
            throw new AuthorizationException();
        }

        return Member.from(memberRepository.getMemberInfo(memberId));
    }
}
