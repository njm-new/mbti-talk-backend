package com.mbtitalkbackend.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mbtitalkbackend.client.kakao.KakaoClient;
import com.mbtitalkbackend.common.SnsType;
import com.mbtitalkbackend.member.model.dto.MemberDTO;
import com.mbtitalkbackend.member.exception.KakaoAuthenticationException;
import com.mbtitalkbackend.member.model.vo.LoginRequestVO;
import com.mbtitalkbackend.member.repository.MemberRepository;
import com.mbtitalkbackend.util.authrization.AccessTokenManager;
import com.mbtitalkbackend.util.authrization.AuthorizationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final KakaoClient kakaoClient;
    private final MemberRepository memberRepository;

    public MemberDTO login(LoginRequestVO loginRequestVO) throws IllegalAccessException {
        String memberId;
        String snsType = loginRequestVO.getSnsType();
        String snsCode = loginRequestVO.getSnsCode();

        switch (SnsType.compare(snsType)) {
            case KAKAO:
                try {
                    //Authenticate user from Kakao
                    String kakaoAccessToken = loginRequestVO.getSnsAccessToken();
                    if (StringUtils.isBlank(kakaoAccessToken)) {
                        kakaoAccessToken = kakaoClient.getAccessToken(snsCode);
                    }

                    memberId = SnsType.KAKAO.name().toLowerCase() + '-' + kakaoClient.getMemberId(kakaoAccessToken);
                } catch (JsonProcessingException e) {
                    log.error(e.getMessage());
                    throw new KakaoAuthenticationException();
                }
                break;

            default:
                log.error(snsType);
                throw new IllegalAccessException();
        }
        //Check user
        MemberDTO member = memberRepository.getMemberInfo(memberId);

        //if newbie == signUp
        if (member == null) {
            //Generate nickname
            String memberNickname = createNickName();

            //Insert member data into DB
            member = MemberDTO.of(memberId, memberNickname);
            memberRepository.register(member);
        }
        return member;
    }


    public MemberDTO getInfo(String memberId) {
        return memberRepository.getMemberInfo(memberId);
    }

    public void update(MemberDTO memberDTO) {
        memberRepository.update(memberDTO);
    }

    public boolean existNickname(String memberId, String nickname) {
        if (nickname == null) {
            return true;
        }
        return memberRepository.existNickname(memberId, nickname);
    }

    public String getMemberIdFromAccessToken(String accessToken) {
        String tokenHeader = "Bearer";

        //Validate Authorization Header
        if (accessToken == null || !accessToken.startsWith(tokenHeader)) {
            throw new IllegalStateException();
        }

        //Get token string
        String tokenString = accessToken.substring(tokenHeader.length());
        Claims claims;

        //Get token Claim
        try {
            claims = Jwts
                    .parser()
                    .setSigningKey(AccessTokenManager.SALT)
                    .parseClaimsJws(tokenString)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return (String) e.getClaims().get("memberId");
        } catch (Exception e) {
            throw new AuthorizationException();
        }

        return (String) claims.get("memberId");
    }

    public boolean checkAuth(String memberId, String reqId) {
        return memberId.equals(reqId);
    }

    private String createNickName() {
        String memberNickname;
        Random random = new Random();
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;

        do {
            memberNickname = random.ints(leftLimit, rightLimit + 1)
                    .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
        } while (memberRepository.existNickname(memberNickname));

        return memberNickname;
    }

    /* Local 테스트용 임시 */
    public MemberDTO loginForLocal(LoginRequestVO loginRequestVO) throws IllegalAccessException {
        String memberId;
        String snsType = loginRequestVO.getSnsType();
        String snsCode = loginRequestVO.getSnsCode();

        switch (SnsType.compare(snsType)) {
            case KAKAO:
                try {
                    //Authenticate user from Kakao
                    String kakaoAccessToken = loginRequestVO.getSnsAccessToken();
                    if (StringUtils.isBlank(kakaoAccessToken)) {
                        kakaoAccessToken = kakaoClient.getAccessTokenForLocal(snsCode);
                    }

                    memberId = SnsType.KAKAO.name().toLowerCase() + '-' + kakaoClient.getMemberId(kakaoAccessToken);
                } catch (JsonProcessingException e) {
                    log.error(e.getMessage());
                    throw new KakaoAuthenticationException();
                }
                break;

            default:
                log.error(snsType);
                throw new IllegalAccessException();
        }
        //Check user
        MemberDTO member = memberRepository.getMemberInfo(memberId);

        //if newbie == signUp
        if (member == null) {
            //Generate nickname
            String memberNickname = createNickName();

            //Insert member data into DB
            member = MemberDTO.of(memberId, memberNickname);
            memberRepository.register(member);
        }
        return member;
    }
}
