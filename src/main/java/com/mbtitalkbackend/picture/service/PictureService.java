package com.mbtitalkbackend.picture.service;

import com.mbtitalkbackend.picture.mapper.PictureMapper;
import com.mbtitalkbackend.picture.model.Entity.PictureEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PictureService {

    private final PictureMapper pictureMapper;

    public List<PictureEntity> findAllPictureEntityByPostId(long postId) {
        return pictureMapper.findAllPictureEntityByPostId(postId);
    }
}
