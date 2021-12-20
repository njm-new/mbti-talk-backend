package com.mbtitalkbackend.picture.mapper;

import com.mbtitalkbackend.picture.model.Entity.PictureEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PictureMapper {

    @Select("SELECT * FROM picture WHERE post_id = #{post_id}")
    List<PictureEntity> findAllPictureEntityByPostId(long post_id);

    @Select("UPDATE picture SET picture_name = #{picture_name}, picture_url = #{picture_url}, comment = #{comment}")
    int updatePictureByPictureId(PictureEntity pictureEntity);
}
