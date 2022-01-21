package com.mbtitalkbackend.like.mapper;

import com.mbtitalkbackend.like.model.entity.LikeEntity;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LikeMapper {

    @Insert("INSERT INTO like (postId, userId) VALUES(#{postId}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "likeId")
    Integer createLike(LikeEntity likeEntity);

    @Delete("DELETE FROM like WHERE postId = #{postId} AND userId = #{userId}")
    Integer deleteLike(LikeEntity likeEntity);

    @Select("SELECT * FROM like WHERE postId = #{postId} AND userId = #{userId}")
    LikeEntity findLike(LikeEntity likeEntity);

    @Update("UPDATE post SET likeCount = IFNULL(likeCount, 0) + 1 WHERE postId = #{postId}")
    void increaseLikeCount(long postId);

    @Update("UPDATE post SET likeCount = IFNULL(likeCount, 0) - 1 WHERE postId = #{postId}")
    void decreaseLikeCount(long postId);
}
