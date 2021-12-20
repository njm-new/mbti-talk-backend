package com.mbtitalkbackend.post.mapper;

import com.mbtitalkbackend.post.model.Entity.PostEntity;
import com.mbtitalkbackend.post.model.VO.PostVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PostMapper {

    @Insert("INSERT INTO post (board_id, member_id, title, content, view_count, like_count) VALUES(#{board_id}, #{member_id}, #{title}, #{content}, #{view_count}, #{like_count})")
    @Options(useGeneratedKeys = true, keyProperty = "post_id")
    Integer postPost(PostEntity postEntity);

    @Select("SELECT * FROM post WHERE post_id = #{post_id}")
    PostEntity findPostEntityByPostId(long post_id);

    @Update("UPDATE post SET board_id = #{board_id}, title = #{title}, content = #{content} WHERE post_id = #{post_id}")
    Integer updatePost(PostEntity postEntity);

    @Update("UPDATE post SET view_count = IFNULL(view_count, 0) + 1 WHERE post_id = #{post_id}")
    Integer increaseViewCount(long post_id);

    @Delete("DELETE FROM post WHERE post_id = #{post_id}")
    Integer deletePostByPostId(long post_id);
}
