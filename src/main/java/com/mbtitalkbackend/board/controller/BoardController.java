package com.mbtitalkbackend.board.controller;

import com.mbtitalkbackend.board.model.PagingCriteria;
import com.mbtitalkbackend.board.model.VO.BoardVO;
import com.mbtitalkbackend.board.service.BoardService;
import com.mbtitalkbackend.common.ApiResponse;
import com.mbtitalkbackend.post.model.VO.PostVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<ApiResponse> listAllPost(@RequestBody PagingCriteria pagingCriteria) {

//        List<BoardVO> postEntityList = boardService.listAllPostsWithPaging(pagingCriteria);
        List<BoardVO> postEntityList = boardService.listPosts(pagingCriteria);


        return new ResponseEntity<>(ApiResponse.success(postEntityList), HttpStatus.OK);
    }

    @GetMapping("/{mbti}")
    public ResponseEntity<ApiResponse> listAllWithMBTI(@PathVariable("mbti") String mbti, @RequestBody PagingCriteria pagingCriteria) {

        pagingCriteria.setRowPerPage(12);

//        List<BoardVO> postEntityList = boardService.listAllPostsWithMBTI(pagingCriteria, mbti);
        List<BoardVO> postEntityList = boardService.listPosts(pagingCriteria, mbti);


        return new ResponseEntity<>(ApiResponse.success(postEntityList), HttpStatus.OK);
    }
}
