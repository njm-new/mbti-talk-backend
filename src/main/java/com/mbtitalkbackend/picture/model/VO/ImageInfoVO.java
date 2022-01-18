package com.mbtitalkbackend.picture.model.VO;

import com.mbtitalkbackend.picture.model.Entity.PictureEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageInfoVO {
    private String comment;
    private String pictureName;
    private String pictureUrl;

    public static ImageInfoVO from(PictureEntity pictureEntity) {
        ImageInfoVO imageInfoVO = new ImageInfoVO();

        imageInfoVO.comment = pictureEntity.getComment();
        imageInfoVO.pictureName = pictureEntity.getPictureName();
        imageInfoVO.pictureUrl = pictureEntity.getPictureUrl();

        return imageInfoVO;
    }
}
