package com.mbtitalkbackend.member.model.dto;

import com.mbtitalkbackend.member.model.entity.MemberEntity;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberDTO {
    private String memberId;
    private String nickname;
    private String mbti = null;
    private String content = null;

    private MemberDTO(String memberId, String nickname) {
        this.memberId = memberId;
        this.nickname = nickname;
    }

    public static MemberDTO from(MemberEntity member) {
        return new MemberDTO(member.getMemberId(), member.getNickname(), member.getMbti(), member.getContent());
    }

    public static MemberDTO of(String memberId, String nickname) {
        return new MemberDTO(memberId, nickname);
    }
}
