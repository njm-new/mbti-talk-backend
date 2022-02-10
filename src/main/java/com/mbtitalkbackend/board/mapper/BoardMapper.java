package com.mbtitalkbackend.board.mapper;

import com.mbtitalkbackend.board.model.PagingCriteria;
import com.mbtitalkbackend.post.model.Entity.PostEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BoardMapper {

    @Select("SELECT * FROM post WHERE postId > 0")
    List<PostEntity> findAllPosts();

    @Select("SELECT R1.* FROM (SELECT * FROM post ORDER BY postId DESC ) R1 LIMIT #{offset}, #{rowPerPage}")
    List<PostEntity> findAllPostsWithPaging(int offset, int rowPerPage);

    @Select("SELECT R1.* FROM (SELECT * FROM post WHERE boardId = #{boardId} ORDER BY postId DESC ) R1 LIMIT #{offset}, #{rowPerPage}")
    List<PostEntity> findAllPostsWithBoardId(int offset, int rowPerPage, String boardId);

    @Select("SELECT R1.* FROM (SELECT * FROM post WHERE memberId = #{memberId} ORDER BY postId DESC ) R1 LIMIT #{offset}, #{rowPerPage}")
    List<PostEntity> findMyPosts(int offset, int rowPerPage, String memberId);

    @Select("SELECT R1.* FROM (SELECT * FROM post WHERE boardId = #{boardId} ORDER BY ((likeCount * 10) + viewCount) DESC) R1 LIMIT #{offset}, #{rowPerPage}")
    List<PostEntity> findHotPostsWithBoardId(int offset, int rowPerPage, String boardId);
}
