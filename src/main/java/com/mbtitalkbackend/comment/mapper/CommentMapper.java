package com.mbtitalkbackend.comment.mapper;

import com.mbtitalkbackend.comment.model.entity.CommentEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert("INSERT INTO comment (commentId, postId, memberId, content, likeCount) VALUES(#{commentId}, #{postId}, #{memberId}, #{content}, #{likeCount})")
    Integer createComment(CommentEntity commentEntity);

    @Select("SELECT * FROM comment WHERE commentId = #{commentId}")
    CommentEntity findCommentByCommentId(String commentId);

    @Select("SELECT * FROM comment WHERE postId = #{postId}")
    List<CommentEntity> findCommentListByPostId(String postId);

    @Update("UPDATE comment SET content = #{content} WHERE commentId = #{commentId}")
    Integer updateComment(CommentEntity commentEntity);

    @Delete("DELETE FROM comment WHERE commentId = #{commentId}")
    Integer deleteCommentByCommentId(String commentId);

    @Delete("DELETE FROM comment WHERE postId = #{postId}")
    Integer deleteCommentByPostId(String postId);

    @Update("UPDATE comment SET modifiedTime = now() WHERE commentId = #{commentId}")
    Integer updateCommentModifiedTime(String commentId);

    @Select("SELECT COUNT(*) FROM comment WHERE postId = #{postId}")
    int countCommentByPostId(String postId);

    @Select("SELECT * FROM comment WHERE memberId = #{memberId}")
    List<CommentEntity> findCommentListByMemberId(String memberId);
}
