package com.mbtitalkbackend.member.model.dto;

import com.mbtitalkbackend.member.model.entity.MemberEntity;
import com.mbtitalkbackend.member.model.vo.ChangeRequestVO;
import lombok.Getter;

@Getter
public class MemberDTO {
    private int memberId;
    private String nickname;
    private String mbti = null;
    private String content = null;

    private MemberDTO(int memberId, String nickname) {
        this.memberId = memberId;
        this.nickname = nickname;
    }

    private MemberDTO(int memberId, String nickname, String mbti, String content) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.mbti = mbti;
        this.content = content;
    }

    public static MemberDTO from(ChangeRequestVO member) {
        return new MemberDTO(member.getMemberId(), member.getNickname(), member.getMbti(), member.getContent());
    }

    public static MemberDTO from(MemberEntity member) {
        return new MemberDTO(member.getMemberId(), member.getNickname(), member.getMbti(), member.getContent());
    }

    public static MemberDTO of(int memberId, String nickname) {
        return new MemberDTO(memberId, nickname);
    }
}
