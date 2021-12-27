package com.mbtitalkbackend.picture.mapper;

import com.mbtitalkbackend.picture.model.Entity.PictureEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PictureMapper {

    @Select("SELECT * FROM picture WHERE postId = #{postId}")
    List<PictureEntity> findAllPictureEntityByPostId(long postId);

    @Select("UPDATE picture SET pictureName = #{pictureName}, pictureUrl = #{pictureUrl}, comment = #{comment}")
    int updatePictureByPictureId(PictureEntity pictureEntity);
}
