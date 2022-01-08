package com.mbtitalkbackend.member.model.dto;

import com.mbtitalkbackend.member.model.entity.MemberEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberDTO {
    private int memberId;
    private String nickname;
    private String mbti = null;
    private String content = null;

    private MemberDTO(int memberId, String nickname) {
        this.memberId = memberId;
        this.nickname = nickname;
    }

    public static MemberDTO from(MemberEntity member) {
        return new MemberDTO(member.getMemberId(), member.getNickname(), member.getMbti(), member.getContent());
    }

    public static MemberDTO of(int memberId, String nickname) {
        return new MemberDTO(memberId, nickname);
    }
}
