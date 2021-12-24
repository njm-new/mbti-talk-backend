package com.mbtitalkbackend.member.model.dto;

import com.mbtitalkbackend.member.model.entity.MemberEntity;
import com.mbtitalkbackend.member.model.vo.ChangeRequestVO;
import lombok.Data;

@Data
public class MemberDTO {
    private int memberId;
    private String nickname;
    private String memberMbti = null;
    private String content = null;

    private MemberDTO(int memberId, String nickname) {
        this.memberId = memberId;
        this.nickname = nickname;
    }

    private MemberDTO(int memberId, String nickname, String memberMbti, String content) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.memberMbti = memberMbti;
        this.content = content;
    }

    public static MemberDTO from(ChangeRequestVO member) {
        return new MemberDTO(member.getMemberId(), member.getNickname(), member.getMemberMbti(), member.getContent());
    }

    public static MemberDTO from(MemberEntity member) {
        return new MemberDTO(member.getMemberId(), member.getNickname(), member.getMemberMbti(), member.getContent());
    }

    public static MemberDTO of(int memberId, String nickname) {
        return new MemberDTO(memberId, nickname);
    }
}
