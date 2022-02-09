package com.mbtitalkbackend.like.controller;

import com.mbtitalkbackend.common.ApiResponse;
import com.mbtitalkbackend.like.model.entity.LikeEntity;
import com.mbtitalkbackend.like.service.LikeService;
import com.mbtitalkbackend.member.model.vo.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/posts/{postId}")
public class LikeController {

    public final LikeService likeService;

    @PostMapping("/like")
    public ResponseEntity<ApiResponse> hitLike(@PathVariable String postId, Member member) {

        likeService.like(LikeEntity.create(postId, member.getMemberId()));
        return new ResponseEntity<>(ApiResponse.success(), HttpStatus.OK);
    }

    @DeleteMapping("/like")
    public ResponseEntity<ApiResponse> cancelLike(@PathVariable String postId, Member member) {

        likeService.unLike(LikeEntity.create(postId, member.getMemberId()));
        return new ResponseEntity<>(ApiResponse.success(), HttpStatus.OK);
    }
}
