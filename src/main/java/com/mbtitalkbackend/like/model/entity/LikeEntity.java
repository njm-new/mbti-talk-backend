package com.mbtitalkbackend.like.model.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LikeEntity {

    private int likeIdx;
    private int postId;
    private int userId;
    private int likeCheck;
}
