package com.mbtitalkbackend.member.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class MemberEntity {
    private int memberId;
    private String nickname;
    private String memberMbti;
    private String content;
    private Date createTime;
    private Date modifiedTime;
}
