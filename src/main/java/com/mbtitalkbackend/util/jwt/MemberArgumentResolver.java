package com.mbtitalkbackend.util.jwt;

import com.mbtitalkbackend.member.model.vo.Member;
import com.mbtitalkbackend.member.repository.MemberRepository;
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
        return parameter.getParameterAnnotation(MemberInfo.class) != null
                && parameter.getParameterType().equals(Member.class);
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
                throw new JwtValidationException();
            }

            token = token.substring(tokenHeader.length());

            memberId = (int) Jwts
                    .parser()
                    .setSigningKey(JsonWebToken.SALT)
                    .parseClaimsJws(token)
                    .getBody()
                    .get("memberId");

        } catch (Exception e) {
            throw new JwtValidationException();
        }

        return Member.from(memberRepository.getMemberInfo(memberId));
    }
}
