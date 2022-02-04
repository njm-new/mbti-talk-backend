package com.mbtitalkbackend.like.controller;

import com.mbtitalkbackend.common.ApiResponse;
import com.mbtitalkbackend.like.model.entity.LikeEntity;
import com.mbtitalkbackend.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts/{postId}")
public class LikeController {

    public final LikeService likeService;

    @PostMapping("/like")
    public ResponseEntity<ApiResponse> hitLike(@PathVariable long postId, @RequestBody LikeEntity likeEntity) {

        likeService.hitLike(likeEntity);
        return new ResponseEntity<>(ApiResponse.success(), HttpStatus.OK);
    }

    @DeleteMapping("/like")
    public ResponseEntity<ApiResponse> cancelLike(@PathVariable long postId, @RequestBody LikeEntity likeEntity) {

        likeService.cancelLike(likeEntity);
        return new ResponseEntity<>(ApiResponse.success(), HttpStatus.OK);
    }
}
