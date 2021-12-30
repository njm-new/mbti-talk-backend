package com.mbtitalkbackend.member.model.entity;

import com.mbtitalkbackend.member.model.dto.MemberDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberEntity {
    private int memberId;
    private String nickname;
    private String mbti;
    private String content;
    private Date createTime;
    private Date modifiedTime;

    public static MemberEntity of(MemberDTO member, Date createTime, Date modifiedTime) {
        return new MemberEntity(member.getMemberId(), member.getNickname(), member.getMbti(), member.getContent(), createTime, modifiedTime);
    }
}
