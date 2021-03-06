package com.mbtitalkbackend.picture.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.mbtitalkbackend.picture.mapper.PictureMapper;
import com.mbtitalkbackend.picture.model.Entity.PictureEntity;
import com.mbtitalkbackend.picture.model.VO.ImageVO;
import com.mbtitalkbackend.picture.model.VO.ImageVOList;
import com.mbtitalkbackend.picture.model.VO.ImageInfoVO;
import com.mbtitalkbackend.util.generator.IdGenerator;
import com.mbtitalkbackend.util.generator.ServiceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PictureService {

    private final AwsS3UploadService s3Service;
    private final PictureMapper pictureMapper;
    private final IdGenerator idGenerator;

    public List<ImageInfoVO> uploadImages(String postId, ImageVOList imageVOList) {

        List<ImageInfoVO> imageInfoVOList = new ArrayList<>();

        imageVOList.getImageVOList().forEach(imageVO -> imageInfoVOList.add(uploadImage(postId, imageVO)));

        return imageInfoVOList;
    }

    public ImageInfoVO uploadImage(String postId, ImageVO imageVO) {

        String fileName = createFileName(imageVO.getFile().getOriginalFilename());

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(imageVO.getFile().getSize());
        objectMetadata.setContentType(imageVO.getFile().getContentType());

        try (InputStream inputStream = imageVO.getFile().getInputStream()) {
            s3Service.uploadFiles(inputStream, objectMetadata, fileName);
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("파일 변환 중 에러가 발생하였습니다. (%s)", imageVO.getFile().getOriginalFilename()));
//                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
        }
        String pictureId = idGenerator.generate(ServiceType.PICTURE);
        PictureEntity pictureEntity = PictureEntity.of(pictureId, postId, fileName, s3Service.getFileUrl(fileName), imageVO.getComment());
        pictureMapper.insertPicture(pictureEntity);

        return ImageInfoVO.from(pictureEntity);
    }

    private String createFileName(String originalFileName) {
        return UUID.randomUUID().toString().concat(originalFileName);
    }

    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(String.format("잘못된 형식의 파일 (%s) 입니다.", fileName));
        }
    }

    public List<ImageInfoVO> listPictureVOListByPostID(String postId) {
        List<PictureEntity> pictureEntityList = pictureMapper.findAllPictureEntityByPostId(postId);
        List<ImageInfoVO> imageInfoVOList = new ArrayList<>();

        pictureEntityList.forEach(pictureEntity -> imageInfoVOList.add(ImageInfoVO.from(pictureEntity)));

        return imageInfoVOList;
    }

    public void deletePicture(String pictureName) {
        s3Service.deleteFile(pictureName);
        pictureMapper.deletePicture(pictureName);
    }

    public void updatePicture(String pictureName, String postId, String comment, List<MultipartFile> files) {
        deletePicture(pictureName);
    }
}
