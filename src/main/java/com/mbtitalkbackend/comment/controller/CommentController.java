package com.mbtitalkbackend.comment.controller;

import com.mbtitalkbackend.comment.model.VO.CommentVOList;
import com.mbtitalkbackend.comment.model.VO.CommentVO;
import com.mbtitalkbackend.comment.service.CommentService;
import com.mbtitalkbackend.common.ApiResponse;
import com.mbtitalkbackend.member.model.vo.Member;
import com.mbtitalkbackend.util.generator.IdGenerator;
import com.mbtitalkbackend.util.generator.ServiceType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;
    private final IdGenerator idGenerator;

    @GetMapping
    public ResponseEntity<ApiResponse> getCommentList(@PathVariable String postId) {
        CommentVOList commentVOList = commentService.findCommentList(postId);

        return new ResponseEntity<>(ApiResponse.success(commentVOList), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createComment(@PathVariable String postId, @RequestBody CommentVO commentVO, Member member) {

        String commentId = idGenerator.generate(ServiceType.COMMENT);
        commentService.createComment(CommentVO.create(commentId, postId, commentVO.getContent(), member));

        return new ResponseEntity<>(ApiResponse.success(), HttpStatus.OK);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<ApiResponse> patchComment(@PathVariable String postId, @PathVariable String commentId, @RequestBody CommentVO commentVO, Member member) {

        commentService.updateComment(CommentVO.create(commentId, postId, commentVO.getContent(), member));

        return new ResponseEntity<>(ApiResponse.success(), HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable String postId, @PathVariable String commentId) {

        commentService.deleteCommentByCommentId(commentId);

        return new ResponseEntity<>(ApiResponse.success(), HttpStatus.OK);
    }
}
