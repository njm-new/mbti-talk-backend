package com.mbtitalkbackend.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mbtitalkbackend.client.KakaoClient;
import com.mbtitalkbackend.member.mapper.MemberMapper;
import com.mbtitalkbackend.member.model.dto.MemberDTO;
import com.mbtitalkbackend.member.exception.KakaoAuthenticationException;
import com.mbtitalkbackend.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MemberServiceImpl implements MemberService {
    private final KakaoClient kakaoClient;
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    public MemberServiceImpl(KakaoClient kakaoClient, MemberRepository memberRepository, MemberMapper memberMapper) {
        this.kakaoClient = kakaoClient;
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    @Override
    public MemberDTO login(String kakaoCode) {
        //Authenticate user from Kakao
        int memberId;

        try {
            String kakaoAccessToken = kakaoClient.getAccessToken(kakaoCode);
            memberId = kakaoClient.getMemberId(kakaoAccessToken);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new KakaoAuthenticationException();
        }
        //Check user
        MemberDTO member = memberRepository.getMemberInfo(memberId);

        //if newbie == signUp
        if (member == null) {
            //Generate nickname
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

            //Insert member data into DB
            member = MemberDTO.of(memberId, memberNickname);
            memberRepository.signUp(member);
        }
        return member;
    }

    @Override
    public boolean update(MemberDTO memberDTO) {
        return memberRepository.update(memberDTO);
    }

    @Override
    public boolean existNickname(String nickname) {
        return memberRepository.existNickname(nickname);
    }
}
