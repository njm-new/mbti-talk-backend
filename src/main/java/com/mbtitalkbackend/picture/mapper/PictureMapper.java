package com.mbtitalkbackend.picture.mapper;

import com.mbtitalkbackend.picture.model.Entity.PictureEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PictureMapper {

    @Insert("INSERT INTO picture (pictureId, postId, pictureName, pictureUrl, comment) VALUES (#{pictureId}, #{postId}, #{pictureName}, #{pictureUrl}, #{comment})")
    Integer insertPicture(PictureEntity pictureEntity);

    @Select("SELECT * FROM picture WHERE postId = #{postId}")
    List<PictureEntity> findAllPictureEntityByPostId(String postId);

    @Select("UPDATE picture SET pictureName = #{pictureName}, pictureUrl = #{pictureUrl}, comment = #{comment}")
    Integer updatePictureByPictureId(PictureEntity pictureEntity);

    @Delete("DELETE FROM picture WHERE pictureName = #{pictureName}")
    Integer deletePicture(String pictureName);
}
