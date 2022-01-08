package com.mbtitalkbackend.member.repository;

import com.mbtitalkbackend.member.mapper.MemberMapper;
import com.mbtitalkbackend.member.model.dto.MemberDTO;
import com.mbtitalkbackend.member.model.entity.MemberEntity;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
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

        return MemberDTO.from(memberEntity);
    }

    public boolean signUp(MemberDTO member) {
        Date date = new Date();
        return memberMapper.insertMember(MemberEntity.of(member, date, date)) > 0;
    }

    public boolean existNickname(String nickname) {
        return memberMapper.existNicknameAll(nickname) > 0;
    }
    public boolean existNickname(int memberId, String nickname) {
        return memberMapper.existNickname(memberId, nickname) > 0;
    }

    public boolean update(MemberDTO member) {
        Date date = new Date();
        return memberMapper.patchMember(MemberEntity.of(member, date, date)) > 0;
    }

    public boolean updateMbti(int memberId, String mbti) {
        return memberMapper.putMbti(memberId, mbti) > 0;
    }

    public boolean updateNickname(int memberId, String nickname) {
        return memberMapper.putMbti(memberId, nickname) > 0;
    }
}
