package com.mbtitalkbackend.member.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChangeRequestVO {
    private int memberId;
    private String nickname;
    private String mbti;
    private String content;
}
