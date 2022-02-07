package com.mbtitalkbackend.picture.model.Entity;

import lombok.*;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PictureEntity {

    private long pictureId;
    private String postId;
    private long commentId;
    private String pictureName;
    private String pictureUrl;
    private String comment;
    private Timestamp createTime;
    private Timestamp modifiedTime;

    public static PictureEntity of (String postId, String pictureName, String pictureUrl, String comment) {
        PictureEntity pictureEntity = new PictureEntity();

        pictureEntity.postId = postId;
        pictureEntity.pictureName = pictureName;
        pictureEntity.pictureUrl = pictureUrl;
        pictureEntity.comment = comment;

        return pictureEntity;
    }
}
