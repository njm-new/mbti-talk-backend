package com.mbtitalkbackend.member.mapper;

import com.mbtitalkbackend.member.model.entity.MemberEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MemberMapper {
    @Insert("INSERT INTO member (memberId, nickname, memberMbti, content, createTime, modifiedTime) VALUES (#{memberId}, #{nickname}, #{memberMbti}, #{content}, #{createTime}, #{modifiedTime})")
    Integer insertMember(MemberEntity member);

    @Select("SELECT * FROM member WHERE memberId = #{memberId}")
    MemberEntity findMemberById(int memberId);


    @Select("SELECT COUNT(nickname) FROM member WHERE nickname = #{nickname}")
    Integer existNickname(String nickname);

    @Update("UPDATE member SET nickname=#{nickname}, memberMbti=#{memberMbti}, content=#{content}, modifiedTime=#{modifiedTime} WHERE memberId=#{memberId}")
    Integer patchMember(MemberEntity member);
}
