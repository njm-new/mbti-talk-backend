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

    public MemberDTO getMemberInfo(String memberId) {
        MemberEntity memberEntity = memberMapper.findMemberById(memberId);

        if (memberEntity == null) {
            //todo null 예외처리
            return null;
        }

        return MemberDTO.from(memberEntity);
    }

    public boolean register(MemberDTO member) {
        return memberMapper.insertMember(MemberEntity.from(member)) > 0;
    }

    public boolean existNickname(String nickname) {
        return memberMapper.existNicknameAll(nickname) > 0;
    }

    public boolean existNickname(String memberId, String nickname) {
        return memberMapper.existNickname(memberId, nickname) > 0;
    }

    public boolean update(MemberDTO member) {
        return memberMapper.patchMember(MemberEntity.from(member)) > 0;
    }
}
