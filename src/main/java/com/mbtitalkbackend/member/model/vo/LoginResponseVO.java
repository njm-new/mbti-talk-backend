package com.mbtitalkbackend.member.model.vo;

import com.mbtitalkbackend.member.model.dto.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseVO {
    String jwt;
    MemberDTO memberInfo;
}
