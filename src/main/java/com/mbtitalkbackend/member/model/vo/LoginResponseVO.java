package com.mbtitalkbackend.member.model.vo;

import com.mbtitalkbackend.member.model.dto.MemberDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginResponseVO {
    String jwt;
    MemberDTO member;

    public static LoginResponseVO of(String jwt, MemberDTO member) {
        return new LoginResponseVO(jwt, member);
    }
}
