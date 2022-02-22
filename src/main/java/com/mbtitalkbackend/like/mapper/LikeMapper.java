package com.mbtitalkbackend.like.mapper;

import com.mbtitalkbackend.like.model.entity.LikeEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LikeMapper {

    @Insert("INSERT INTO memberLike (likeId, postId, memberId) VALUES(#{likeId}, #{postId}, #{memberId})")
    Integer createLike(LikeEntity likeEntity);

    @Delete("DELETE FROM memberLike WHERE postId = #{postId} AND memberId = #{memberId}")
    Integer deleteLike(LikeEntity likeEntity);

    @Select("SELECT * FROM memberLike WHERE postId = #{postId} AND memberId = #{memberId}")
    LikeEntity findLike(LikeEntity likeEntity);

    @Update("UPDATE post SET likeCount = IFNULL(likeCount, 0) + 1 WHERE postId = #{postId}")
    void increaseLikeCount(String postId);

    @Update("UPDATE post SET likeCount = IFNULL(likeCount, 0) - 1 WHERE postId = #{postId}")
    void decreaseLikeCount(String postId);

    @Select("SELECT * FROM memberLike WHERE memberId = #{memberId}")
    List<LikeEntity> findLikeByMemberId(String memberId);
}
