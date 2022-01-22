package com.mbtitalkbackend.board.controller;

import com.mbtitalkbackend.board.model.PagingCriteria;
import com.mbtitalkbackend.board.model.VO.BoardVO;
import com.mbtitalkbackend.board.model.VO.RequestVO;
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
    public ResponseEntity<ApiResponse> listAllPost(@RequestBody RequestVO requestVO) {

//        List<BoardVO> boardVOList = boardService.listAllPostsWithPaging(pagingCriteria);
        List<BoardVO> boardVOList = boardService.listPosts(requestVO.getPagingCriteria());


        return new ResponseEntity<>(ApiResponse.success(boardVOList), HttpStatus.OK);
    }

    @GetMapping("/{mbti}")
    public ResponseEntity<ApiResponse> listAllWithMBTI(@PathVariable("mbti") String mbti, @RequestBody RequestVO requestVO) {

        requestVO.getPagingCriteria().setRowPerPage(12);

//        List<BoardVO> boardVOList = boardService.listAllPostsWithMBTI(pagingCriteria, mbti);
        List<BoardVO> boardVOList = boardService.listPosts(requestVO.getPagingCriteria(), mbti);


        return new ResponseEntity<>(ApiResponse.success(boardVOList), HttpStatus.OK);
    }

    @GetMapping("/myPosts")
    public ResponseEntity<ApiResponse> listMyPosts(@RequestBody RequestVO requestVO) {
        List<BoardVO> boardVOList = boardService.listPosts(requestVO.getPagingCriteria(), requestVO.getMemberId());
        return new ResponseEntity<>(ApiResponse.success(boardVOList), HttpStatus.OK);
    }

    @GetMapping("/myComment")
    public ResponseEntity<ApiResponse> listMyCommentPosts(@RequestBody RequestVO requestVO) {
        List<BoardVO> boardVOList = boardService.listCommentPosts(requestVO);
        return new ResponseEntity<>(ApiResponse.success(boardVOList), HttpStatus.OK);
    }

    @GetMapping("/myLike")
    public ResponseEntity<ApiResponse> listMyLikePosts(@RequestBody RequestVO requestVO) {
        List<BoardVO> boardVOList = boardService.listLikePosts(requestVO);
        return new ResponseEntity<>(ApiResponse.success(boardVOList), HttpStatus.OK);
    }

    @GetMapping("/hot")
    public ResponseEntity<ApiResponse> listHotPosts(@RequestBody RequestVO requestVO) {
        return new ResponseEntity<>(ApiResponse.success(), HttpStatus.OK);
    }

    @GetMapping("/{mbti}/hot")
    public ResponseEntity<ApiResponse> listHotPostsInMbti(@PathVariable String mbti, @RequestBody RequestVO requestVO) {
        return new ResponseEntity<>(ApiResponse.success(), HttpStatus.OK);
    }
}
