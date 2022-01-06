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

    @Select("SELECT * FROM post WHERE postId BETWEEN (#{pageNum} - 1) * #{rowPerPage} AND #{pageNum} * #{rowPerPage}")
    List<PostEntity> findAllPostsWithPaging(PagingCriteria pagingCriteria);

    @Select("SELECT * FROM post WHERE boardId = #{mbti} AND postId BETWEEN (#{pagingCriteria.pageNum} - 1) * #{pagingCriteria.rowPerPage} AND #{pagingCriteria.pageNum} * #{pagingCriteria.rowPerPage}")
    List<PostEntity> findAllPostsWithMBTI(PagingCriteria pagingCriteria, String mbti);
}
