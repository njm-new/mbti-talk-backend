package com.mbtitalkbackend.picture.model.VO;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageVOList {

    List<ImageVO> imageVOList;
}

