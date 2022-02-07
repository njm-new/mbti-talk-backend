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

    @Select("SELECT * FROM post BETWEEN #{pageNum} * #{rowPerPage} AND (#{pageNum} + 1) * #{rowPerPage}")
    List<PostEntity> findAllPostsWithPaging(PagingCriteria pagingCriteria);

    @Select("SELECT * FROM post WHERE boardId = #{boardId} AND postId BETWEEN #{pagingCriteria.pageNum} * #{pagingCriteria.rowPerPage} AND (#{pagingCriteria.pageNum} + 1) * #{pagingCriteria.rowPerPage}")
    List<PostEntity> findAllPostsWithBoardId(PagingCriteria pagingCriteria, String boardId);

    @Select("SELECT * FROM post WHERE memberId = #{memberId} AND postId BETWEEN #{pagingCriteria.pageNum} * #{pagingCriteria.rowPerPage} AND (#{pagingCriteria.pageNum} + 1) * #{pagingCriteria.rowPerPage}")
    List<PostEntity> findMyPosts(PagingCriteria pagingCriteria, String memberId);

    @Select("SELECT R1.* FROM (SELECT * FROM post WHERE boardId = #{boardId} ORDER BY ((likeCount * 10) + viewCount) DESC) R1 LIMIT #{pagingCriteria.pageNum}, #{pagingCriteria.rowPerPage}")
    List<PostEntity> findHotPostsWithBoardId(PagingCriteria pagingCriteria, String boardId);
}
