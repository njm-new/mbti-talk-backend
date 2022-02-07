package com.mbtitalkbackend.post.mapper;

import com.mbtitalkbackend.post.model.Entity.PostEntity;
import org.apache.ibatis.annotations.*;

@Mapper
public interface PostMapper {

    @Insert("INSERT INTO post (boardId, memberId, title, content, viewCount, likeCount) VALUES(#{boardId}, #{memberId}, #{title}, #{content}, #{viewCount}, #{likeCount})")
    @Options(useGeneratedKeys = true, keyProperty = "postId")
    Integer postPost(PostEntity postEntity);

    @Select("SELECT * FROM post WHERE postId = #{postId}")
    PostEntity findPostEntityByPostId(String postId);

    @Update("UPDATE post SET boardId = #{boardId}, title = #{title}, content = #{content} WHERE postId = #{postId}")
    Integer updatePost(PostEntity postEntity);

    @Update("UPDATE post SET viewCount = IFNULL(viewCount, 0) + 1 WHERE postId = #{postId}")
    void increaseViewCount(String postId);

    @Delete("DELETE FROM post WHERE postId = #{postId}")
    Integer deletePostByPostId(String postId);

    @Update("UPDATE post SET modifiedTime = now() WHERE postId = #{postId}")
    Integer updatePostModifiedTime(String postId);
}
