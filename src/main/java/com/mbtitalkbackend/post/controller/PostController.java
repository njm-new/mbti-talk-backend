package com.mbtitalkbackend.post.controller;

import com.mbtitalkbackend.common.model.ResultVO;
import com.mbtitalkbackend.post.model.Entity.PostEntity;
import com.mbtitalkbackend.post.model.VO.PostVO;
import com.mbtitalkbackend.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResultVO createPosts(@RequestBody PostVO postVO) {

        int res = postService.createPost(postVO);

        if(res > 0)
            return new ResultVO(HttpStatus.OK, "success"); // HTTP-STATUS.OK
        else
            return new ResultVO(HttpStatus.BAD_REQUEST, "failed"); // HTTP-STATUS.OK
    }

    @GetMapping("/{post_id}") // 호출할일 없고, 디자인 잘해놨으면 메소드가 구분할 필요가없다
    public Object readPostsByPostId(@PathVariable("post_id") long post_id) { //리턴 타입 DTO로 설정, 카멜케이스로 변경

        PostEntity postEntity = postService.findPostEntityById(post_id);//서비스 안에서 처리

        if(postEntity == null)
            return new ResultVO(HttpStatus.NOT_FOUND, "fail");
        else
            return new ResultVO(HttpStatus.OK, "success", postEntity);
    }

    @PatchMapping("/{post_id}")
    public ResultVO updatePostByPostId(@PathVariable("post_id") long post_id, @RequestBody PostVO postVO) {

        int res = postService.patchPostById(post_id, postVO);

        if(res > 0)
            return new ResultVO(HttpStatus.OK, "success");
        else
            return new ResultVO(HttpStatus.BAD_REQUEST, "fail");
    }

    @DeleteMapping("/{post_id}")
    public ResultVO deletePostByPostId(@PathVariable("post_id") long post_id) {

        int res = postService.deletePostById(post_id);

        if(res > 0)
            return new ResultVO(HttpStatus.OK, "success");
        else
            return new ResultVO(HttpStatus.BAD_REQUEST, "fail");
    }
}
