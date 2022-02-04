package com.mbtitalkbackend.like.model.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LikeEntity {

    private int likeId;
    private int postId;
    private int memberId;
    private int likeCheck;
}
