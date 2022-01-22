package com.mbtitalkbackend.picture.model.VO;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageVO {
    String comment;
    MultipartFile file;
}
