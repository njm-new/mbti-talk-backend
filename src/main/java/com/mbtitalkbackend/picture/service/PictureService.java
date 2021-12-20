package com.mbtitalkbackend.picture.service;

import com.mbtitalkbackend.picture.model.Entity.PictureEntity;

import java.util.List;

public interface PictureService {

    List<PictureEntity> findAllPictureEntityByPostId(long post_id);
}
