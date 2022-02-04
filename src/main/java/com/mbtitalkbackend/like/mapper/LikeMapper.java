package com.mbtitalkbackend.like.mapper;

import com.mbtitalkbackend.like.model.entity.LikeEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LikeMapper {

    @Insert("INSERT INTO memberLike (postId, memberId) VALUES(#{postId}, #{memberId})")
    @Options(useGeneratedKeys = true, keyProperty = "likeId")
    Integer createLike(LikeEntity likeEntity);

    @Delete("DELETE FROM memberLike WHERE postId = #{postId} AND memberId = #{memberId}")
    Integer deleteLike(LikeEntity likeEntity);

    @Select("SELECT * FROM memberLike WHERE postId = #{postId} AND memberId = #{memberId}")
    LikeEntity findLike(LikeEntity likeEntity);

    @Update("UPDATE post SET likeCount = IFNULL(likeCount, 0) + 1 WHERE postId = #{postId}")
    void increaseLikeCount(long postId);

    @Update("UPDATE post SET likeCount = IFNULL(likeCount, 0) - 1 WHERE postId = #{postId}")
    void decreaseLikeCount(long postId);

    @Select("SELECT * FROM memberLike WHERE memberId = #{memberId}")
    List<LikeEntity> findLikeByMemberId(int memberId);
}
