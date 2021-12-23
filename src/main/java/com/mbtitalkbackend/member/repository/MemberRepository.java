package com.mbtitalkbackend.member.repository;

import com.mbtitalkbackend.member.mapper.MemberMapper;
import com.mbtitalkbackend.member.model.dto.MemberDTO;
import com.mbtitalkbackend.member.model.entity.MemberEntity;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class MemberRepository {
    private final MemberMapper memberMapper;

    public MemberRepository(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    public MemberDTO getMemberInfo(int memberId) {
        MemberEntity memberEntity = memberMapper.findMemberById(memberId);

        if (memberEntity == null) {
            return null;
        }

        return MemberDTO.of(memberEntity.getMemberId(), memberEntity.getNickname());
    }

    public boolean signUp(MemberDTO member) {
        Date date = new Date();
        return memberMapper.insertMember(new MemberEntity(member.getMemberId(), member.getNickname(), member.getMemberMbti(), member.getContent(), date, date)) > 0;
    }

    public boolean existNickname(String nickname) {
        return memberMapper.existNickname(nickname) > 0;
    }

    public boolean update(MemberDTO member) {
        Date date = new Date();
        return memberMapper.patchMember(new MemberEntity(member.getMemberId(), member.getNickname(), member.getMemberMbti(), member.getContent(), date, date)) > 0;
    }
}
