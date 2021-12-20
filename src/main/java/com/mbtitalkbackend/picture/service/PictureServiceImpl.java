package com.mbtitalkbackend.picture.service;

import com.mbtitalkbackend.picture.mapper.PictureMapper;
import com.mbtitalkbackend.picture.model.Entity.PictureEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PictureServiceImpl implements PictureService{

    private final PictureMapper pictureMapper;

    @Override
    public List<PictureEntity> findAllPictureEntityByPostId(long post_id) {
        return pictureMapper.findAllPictureEntityByPostId(post_id);
    }
}
