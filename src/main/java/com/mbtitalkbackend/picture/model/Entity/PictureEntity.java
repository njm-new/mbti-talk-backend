package com.mbtitalkbackend.picture.model.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PictureEntity {

    private long picture_id;
    private long post_id;
    private String picture_name;
    private String picture_url;
    private String comment;
    private Timestamp create_time;
    private Timestamp modified_time;
}
