package com.mbtitalkbackend.picture.model.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
public class PictureEntity {

    private long pictureId;
    private long postId;
    private String pictureName;
    private String pictureUrl;
    private String comment;
    private Timestamp createTime;
    private Timestamp modifiedTime;
}
