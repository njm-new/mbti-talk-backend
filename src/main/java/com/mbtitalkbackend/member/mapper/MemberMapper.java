package com.mbtitalkbackend.member.mapper;

import com.mbtitalkbackend.member.model.entity.MemberEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MemberMapper {
    @Insert("INSERT INTO member (memberId, nickname, mbti, content) VALUES (#{memberId}, #{nickname}, #{mbti}, #{content})")
    Integer insertMember(MemberEntity member);

    @Select("SELECT * FROM member WHERE memberId = #{memberId}")
    MemberEntity findMemberById(String memberId);

    @Select("SELECT COUNT(nickname) FROM member WHERE nickname = #{nickname}")
    Integer existNicknameAll(String nickname);

    @Select("SELECT COUNT(nickname) FROM member WHERE nickname = #{nickname} and memberId != #{memberId}")
    Integer existNickname(String memberId, String nickname);

    @Update("UPDATE member SET nickname=#{nickname}, mbti=#{mbti}, content=#{content}, modifiedTime=NOW() WHERE memberId=#{memberId}")
    Integer patchMember(MemberEntity member);
}
