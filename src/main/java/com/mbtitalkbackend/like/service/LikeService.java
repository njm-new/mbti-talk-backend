package com.mbtitalkbackend.like.service;

import com.mbtitalkbackend.like.mapper.LikeMapper;
import com.mbtitalkbackend.like.model.entity.LikeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeMapper likeMapper;

    public void hitLike(LikeEntity likeEntity) throws Exception {

        if(isLike(likeEntity)) {
            throw new Exception();
        } else {
            likeMapper.createLike(likeEntity);
            likeMapper.increaseLikeCount(likeEntity.getPostId());
        }
    }

    public void cancelLike(LikeEntity likeEntity) throws Exception {

        if(likeMapper.deleteLike(likeEntity) == 1)
            likeMapper.decreaseLikeCount(likeEntity.getPostId());
        else
            throw new Exception();
    }

    public boolean isLike(LikeEntity likeEntity) {
        return likeMapper.findLike(likeEntity) != null;
    }
}
