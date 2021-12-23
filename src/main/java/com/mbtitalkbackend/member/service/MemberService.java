package com.mbtitalkbackend.member.service;

import com.mbtitalkbackend.member.model.dto.MemberDTO;

public interface MemberService {
    MemberDTO login(String kakaoCode);
    boolean update(MemberDTO memberDTO);
    boolean existNickname(String nickname);
}
