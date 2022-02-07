package com.mbtitalkbackend.member.model.entity;

import com.mbtitalkbackend.member.model.dto.MemberDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberEntity {
    private String memberId;
    private String nickname;
    private String mbti;
    private String content;
    private Timestamp createTime;
    private Timestamp modifiedTime;

    private MemberEntity(String memberId, String nickname, String mbti, String content) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.mbti = mbti;
        this.content = content;
    }

    public static MemberEntity from(MemberDTO member) {
        return new MemberEntity(member.getMemberId(), member.getNickname(), member.getMbti(), member.getContent());
    }
}
