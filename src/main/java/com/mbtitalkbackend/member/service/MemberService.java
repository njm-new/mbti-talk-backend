package com.mbtitalkbackend.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mbtitalkbackend.client.kakao.KakaoClient;
import com.mbtitalkbackend.member.model.dto.MemberDTO;
import com.mbtitalkbackend.member.exception.KakaoAuthenticationException;
import com.mbtitalkbackend.member.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MemberService {
    private final KakaoClient kakaoClient;
    private final MemberRepository memberRepository;
    private final Logger logger = LoggerFactory.getLogger(MemberService.class);

    public MemberService(KakaoClient kakaoClient, MemberRepository memberRepository) {
        this.kakaoClient = kakaoClient;
        this.memberRepository = memberRepository;
    }

    public MemberDTO login(String kakaoCode) {
        //Authenticate user from Kakao
        int memberId;

        try {
            String kakaoAccessToken = kakaoClient.getAccessToken(kakaoCode);
            memberId = kakaoClient.getMemberId(kakaoAccessToken);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            throw new KakaoAuthenticationException();
        }
        //Check user
        MemberDTO member = memberRepository.getMemberInfo(memberId);

        //if newbie == signUp
        if (member == null) {
            //Generate nickname
            String memberNickname = createNickName();

            //Insert member data into DB
            member = MemberDTO.of(memberId, memberNickname);
            memberRepository.signUp(member);
        }
        return member;
    }

    public MemberDTO getInfo(int memberId) {
        return memberRepository.getMemberInfo(memberId);
    }

    public boolean update(MemberDTO memberDTO) {
        return memberRepository.update(memberDTO);
    }

    public boolean update(int memberId, String mbti) {
        return memberRepository.updateMbti(memberId, mbti);
    }

    public boolean updateNickname(int memberId, String nickname) {
        return memberRepository.updateMbti(memberId, nickname);
    }

    public boolean existNickname(String nickname) {
        if (nickname == null) {
            return true;
        }
        return memberRepository.existNickname(nickname);
    }

    public boolean existNickname(int memberId, String nickname) {
        if (nickname == null) {
            return true;
        }
        return memberRepository.existNickname(memberId, nickname);
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
}
