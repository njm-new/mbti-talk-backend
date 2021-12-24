package com.mbtitalkbackend.member.model.entity;

import com.mbtitalkbackend.member.model.dto.MemberDTO;
import lombok.Getter;

import java.util.Date;

@Getter
public class MemberEntity {
    private int memberId;
    private String nickname;
    private String memberMbti;
    private String content;
    private Date createTime;
    private Date modifiedTime;

    private MemberEntity(int memberId, String nickname, String memberMbti, String content, Date createTime, Date modifiedTime) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.memberMbti = memberMbti;
        this.content = content;
        this.createTime = createTime;
        this.modifiedTime = modifiedTime;
    }

    public static MemberEntity of(MemberDTO member, Date createTime, Date modifiedTime) {
        return new MemberEntity(member.getMemberId(), member.getNickname(), member.getMemberMbti(), member.getContent(), createTime, modifiedTime);
    }
}
