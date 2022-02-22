package com.mbtitalkbackend.like.model.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LikeEntity {

    private String likeId;
    private String postId;
    private String memberId;

    public static LikeEntity create(String postId, String memberId) {
        LikeEntity likeEntity = new LikeEntity();

        likeEntity.postId = postId;
        likeEntity.memberId = memberId;

        return likeEntity;
    }

    public static LikeEntity create(String likeId, String postId, String memberId) {
        LikeEntity likeEntity = new LikeEntity();

        likeEntity.likeId = likeId;
        likeEntity.postId = postId;
        likeEntity.memberId = memberId;

        return likeEntity;
    }
}
