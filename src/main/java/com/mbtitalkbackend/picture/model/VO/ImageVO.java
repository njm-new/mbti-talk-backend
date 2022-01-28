package com.mbtitalkbackend.picture.model.VO;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class ImageVO {
    String comment;
    MultipartFile file;
}
