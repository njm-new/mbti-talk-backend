package com.mbtitalkbackend.comment.controller;

import com.mbtitalkbackend.comment.model.VO.CommentVOList;
import com.mbtitalkbackend.comment.model.VO.CommentVO;
import com.mbtitalkbackend.comment.service.CommentService;
import com.mbtitalkbackend.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<ApiResponse> getCommentList(@PathVariable long postId) {
        CommentVOList commentVOList = commentService.findCommentList(postId);

        return new ResponseEntity<>(ApiResponse.success(commentVOList), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createComment(@PathVariable String postId, @RequestBody CommentVO commentVO) {

        commentService.createComment(commentVO);

        return new ResponseEntity<>(ApiResponse.success(), HttpStatus.OK);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<ApiResponse> patchComment(@PathVariable long postId, @PathVariable long commentId, @RequestBody CommentVO commentVO) {

        commentService.updateComment(commentId, commentVO);

        return new ResponseEntity<>(ApiResponse.success(), HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable long postId, @PathVariable long commentId) {

        commentService.deleteCommentByCommentId(commentId);

        return new ResponseEntity<>(ApiResponse.success(), HttpStatus.OK);
    }
}
