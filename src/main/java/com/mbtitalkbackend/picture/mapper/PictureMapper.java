package com.mbtitalkbackend.picture.mapper;

import com.mbtitalkbackend.picture.model.Entity.PictureEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PictureMapper {

    @Insert("INSERT INTO picture (postId, pictureName, pictureUrl, comment) VALUES (#{postId}, #{pictureName}, #{pictureUrl}, #{comment})")
    @Options(useGeneratedKeys = true, keyProperty = "pictureId")
    Integer insertPicture(PictureEntity pictureEntity);

    @Select("SELECT * FROM picture WHERE postId = #{postId}")
    List<PictureEntity> findAllPictureEntityByPostId(long postId);

    @Select("UPDATE picture SET pictureName = #{pictureName}, pictureUrl = #{pictureUrl}, comment = #{comment}")
    Integer updatePictureByPictureId(PictureEntity pictureEntity);

    @Delete("DELETE FROM picture WHERE pictureName = #{pictureName}")
    Integer deletePicture(String pictureName);
}
