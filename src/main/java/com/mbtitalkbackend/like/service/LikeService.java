package com.mbtitalkbackend.like.service;

import com.mbtitalkbackend.like.mapper.LikeMapper;
import com.mbtitalkbackend.like.model.entity.LikeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeMapper likeMapper;

    public void hitLike(LikeEntity likeEntity) {

        if(!isLike(likeEntity)) {
            likeMapper.createLike(likeEntity);
            likeMapper.increaseLikeCount(likeEntity.getPostId());
        }
    }

    public void cancelLike(LikeEntity likeEntity) {

        if(likeMapper.deleteLike(likeEntity) == 1)
            likeMapper.decreaseLikeCount(likeEntity.getPostId());
    }

    public boolean isLike(LikeEntity likeEntity) {
        return likeMapper.findLike(likeEntity) != null;
    }
}
