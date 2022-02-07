package com.mbtitalkbackend.member.model.vo;

import com.mbtitalkbackend.member.model.dto.MemberDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Member {
    private String memberId;
    private String nickname;
    private String mbti;
    private String content;

    public static Member from(MemberDTO member) {
        return new Member(member.getMemberId(), member.getNickname(), member.getMbti(), member.getContent());
    }
}
