package com.mbtitalkbackend.member.model.vo;

import com.mbtitalkbackend.member.model.dto.MemberDTO;
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class LoginResponseVO {
    String jwt;
    MemberDTO memberInfo;
}
