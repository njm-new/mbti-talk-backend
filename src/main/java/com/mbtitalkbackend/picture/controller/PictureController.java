package com.mbtitalkbackend.picture.controller;

import com.mbtitalkbackend.common.ApiResponse;
import com.mbtitalkbackend.picture.model.VO.ImageVO;
import com.mbtitalkbackend.picture.model.VO.ImageVOList;
import com.mbtitalkbackend.picture.model.VO.ImageInfoVO;
import com.mbtitalkbackend.picture.service.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/posts/{postId}")
public class PictureController {

    private final PictureService pictureService;

    @GetMapping("/images")
    public ResponseEntity<ApiResponse> getImagesUrl(@PathVariable long postId) {
        List<ImageInfoVO> imageInfoVOList = pictureService.listPictureVOListByPostID(postId);
        return new ResponseEntity<>(ApiResponse.success(imageInfoVOList), HttpStatus.OK);
    }

    @PostMapping("/images")
    public ResponseEntity<ApiResponse> uploadImages(@PathVariable long postId, @ModelAttribute ImageVOList imageVOList) {

        List<ImageInfoVO> imageInfoVOList = pictureService.uploadImages(postId, imageVOList);

        return new ResponseEntity<>(ApiResponse.success(imageInfoVOList), HttpStatus.OK);
    }

    @DeleteMapping("/images/{pictureName}")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable long postId, @PathVariable String pictureName) {
        pictureService.deletePicture(pictureName);
        return new ResponseEntity<>(ApiResponse.success(), HttpStatus.OK);
    }


    // 개별 업로드용
    @PostMapping("/image")
    public ResponseEntity<ApiResponse> uploadImage(@PathVariable long postId, @ModelAttribute ImageVO imageVO) {
        ImageInfoVO imageInfoVO = pictureService.uploadImage(postId, imageVO);
        return new ResponseEntity<>(ApiResponse.success(imageInfoVO), HttpStatus.OK);
    }
}