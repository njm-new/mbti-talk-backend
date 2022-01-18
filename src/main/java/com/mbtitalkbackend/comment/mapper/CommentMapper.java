package com.mbtitalkbackend.comment.mapper;

import com.mbtitalkbackend.comment.model.entity.CommentEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert("INSERT INTO comment (postId, memberId, title, content, likeCount) VALUES(#{postId}, #{memberId}, #{title}, #{content}, #{likeCount})")
    @Options(useGeneratedKeys = true, keyProperty = "commentId")
    Integer createComment(CommentEntity commentEntity);

    @Select("SELECT * FROM comment WHERE commentId = #{commentId}")
    CommentEntity findCommentByCommentId(long commentId);

    @Select("SELECT * FROM comment WHERE postId = #{postId}")
    List<CommentEntity> findCommentList(long postId);

    @Update("UPDATE comment SET content = #{content} WHERE commentId = #{commentId}")
    Integer updateComment(CommentEntity commentEntity);

    @Delete("DELETE FROM comment WHERE commentId = #{commentId}")
    Integer deleteCommentByCommentId(long commentId);

    @Update("UPDATE comment SET modifiedTime = now() WHERE commentId = #{commentId}")
    Integer updateCommentModifiedTime(long commentId);

    @Select("SELECT COUNT(*) FROM comment WHERE postId = #{postId}")
    int countCommentByCommentId(long postId);
}