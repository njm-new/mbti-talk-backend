package com.mbtitalkbackend.member.model.dto;

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

    public static MemberDTO of(int memberId, String nickname) {
        return new MemberDTO(memberId, nickname);
    }

    public static class Builder {
        private final int memberId;
        private final String nickname;

        private String memberMbti = null;
        private String content = null;

        public Builder(int memberId, String nickname) {
            this.memberId = memberId;
            this.nickname = nickname;
        }
    }
}
