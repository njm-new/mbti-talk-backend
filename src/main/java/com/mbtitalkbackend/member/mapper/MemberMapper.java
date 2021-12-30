package com.mbtitalkbackend.member.mapper;

import com.mbtitalkbackend.member.model.entity.MemberEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MemberMapper {
    @Insert("INSERT INTO member (memberId, nickname, mbti, content, createTime, modifiedTime) VALUES (#{memberId}, #{nickname}, #{mbti}, #{content}, #{createTime}, #{modifiedTime})")
    Integer insertMember(MemberEntity member);

    @Select("SELECT * FROM member WHERE memberId = #{memberId}")
    MemberEntity findMemberById(int memberId);

    @Select("SELECT COUNT(nickname) FROM member WHERE nickname = #{nickname}")
    Integer existNicknameAll(String nickname);

    @Select("SELECT COUNT(nickname) FROM member WHERE nickname = #{nickname} and memberId != #{memberId}")
    Integer existNickname(int memberId, String nickname);

    @Update("UPDATE member SET nickname=#{nickname}, mbti=#{mbti}, content=#{content}, modifiedTime=#{modifiedTime} WHERE memberId=#{memberId}")
    Integer patchMember(MemberEntity member);

    @Update("UPDATE member SET nickname=#{nickname}, modifiedTime=NOW() WHERE memberId=#{memberId}")
    Integer putNickname(int memberId, String nickname);

    @Update("UPDATE member SET mbti=#{mbti}, modifiedTime=NOW() WHERE memberId=#{memberId}")
    Integer putMbti(int memberId, String mbti);
}
