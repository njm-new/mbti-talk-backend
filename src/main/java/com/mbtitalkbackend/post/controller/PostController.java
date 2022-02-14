package com.mbtitalkbackend.post.controller;

import com.mbtitalkbackend.common.ApiResponse;
import com.mbtitalkbackend.member.model.vo.Member;
import com.mbtitalkbackend.post.model.VO.PostVO;
import com.mbtitalkbackend.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<ApiResponse> createPosts(@RequestBody PostVO postVO, Member member) {
        try {
            PostVO response = postService.createPost(postVO, member);
            return new ResponseEntity<>(ApiResponse.success(response), HttpStatus.OK); // HTTP-STATUS.OK
        } catch (Exception e) {
            // TODO global RestControllerAdvice를 구축해서 try-catch를 빼야 함 (exception도 글로벌리하게)
            return new ResponseEntity<>(ApiResponse.fail("fail"), HttpStatus.BAD_REQUEST); // HTTP-STATUS.OK
        }
    }

    @GetMapping("/{postId}") // 호출할일 없고, 디자인 잘해놨으면 메소드가 구분할 필요가없다
    public ResponseEntity<ApiResponse> readPostsByPostId(@PathVariable("postId") String postId, Member member) { //리턴 타입 DTO로 설정, 카멜케이스로 변경

        try {
            PostVO postVO = postService.findPostEntityById(postId, member);

            return new ResponseEntity<>(ApiResponse.success(postVO), HttpStatus.OK);
        } catch (NullPointerException e) {
            String message = "postId에 해당하는 테이블이 없습니다.";
            return new ResponseEntity<>(ApiResponse.fail(message), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<ApiResponse> updatePostByPostId(@PathVariable("postId") String postId, @RequestBody PostVO postVO) {

        int res = postService.patchPostById(postId, postVO);

        if (res > 0)
            return new ResponseEntity<>(ApiResponse.success(), HttpStatus.OK);
        else
            return new ResponseEntity<>(ApiResponse.fail("fail"), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse> deletePostByPostId(@PathVariable("postId") String postId) {

        int res = postService.deletePostById(postId);

        if (res > 0)
            return new ResponseEntity<>(ApiResponse.success(), HttpStatus.OK);
        else
            return new ResponseEntity<>(ApiResponse.fail("fail"), HttpStatus.BAD_REQUEST);
    }
}
