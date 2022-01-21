package com.mbtitalkbackend.like.service;

import com.mbtitalkbackend.like.mapper.LikeMapper;
import com.mbtitalkbackend.like.model.entity.LikeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeMapper likeMapper;

    public Integer hitLike(LikeEntity likeEntity) {
        likeMapper.increaseLikeCount(likeEntity.getPostId());
        return likeMapper.createLike(likeEntity);
    }

    public Integer cancelLike(LikeEntity likeEntity) {
        likeMapper.decreaseLikeCount(likeEntity.getPostId());
        return likeMapper.deleteLike(likeEntity);
    }

    public boolean isLike(LikeEntity likeEntity) {

        return likeMapper.findLike(likeEntity) != null;
    }
}
