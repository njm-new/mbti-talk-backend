package com.mbtitalkbackend.like.controller;

import com.mbtitalkbackend.common.ApiResponse;
import com.mbtitalkbackend.like.model.entity.LikeEntity;
import com.mbtitalkbackend.like.service.LikeService;
import com.mbtitalkbackend.member.model.vo.Member;
import com.mbtitalkbackend.util.authrization.Authorization;
import com.mbtitalkbackend.util.generator.IdGenerator;
import com.mbtitalkbackend.util.generator.ServiceType;
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
    private final IdGenerator idGenerator;

    @Authorization
    @PostMapping("/like")
    public ResponseEntity<ApiResponse> hitLike(@PathVariable String postId, Member member) {
        String likeId = idGenerator.generate(ServiceType.LIKE);
        likeService.like(LikeEntity.create(likeId, postId, member.getMemberId()));
        return new ResponseEntity<>(ApiResponse.success(), HttpStatus.OK);
    }

    @Authorization
    @DeleteMapping("/like")
    public ResponseEntity<ApiResponse> cancelLike(@PathVariable String postId, Member member) {

        likeService.unLike(LikeEntity.create(postId, member.getMemberId()));
        return new ResponseEntity<>(ApiResponse.success(), HttpStatus.OK);
    }

    @Authorization
    @GetMapping("/like")
    public ResponseEntity<ApiResponse> islike(@PathVariable String postId, Member member) {
        boolean isLike = likeService.isLike(LikeEntity.create(postId, member.getMemberId()));

        return new ResponseEntity<>(ApiResponse.success(isLike), HttpStatus.OK);
    }
}
