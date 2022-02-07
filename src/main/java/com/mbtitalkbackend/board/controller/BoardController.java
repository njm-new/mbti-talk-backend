package com.mbtitalkbackend.board.controller;

import com.mbtitalkbackend.board.model.PagingCriteria;
import com.mbtitalkbackend.board.model.VO.BoardVO;
import com.mbtitalkbackend.board.model.VO.RequestVO;
import com.mbtitalkbackend.board.service.BoardService;
import com.mbtitalkbackend.common.ApiResponse;
import com.mbtitalkbackend.member.model.vo.Member;
import com.mbtitalkbackend.util.authrization.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/guest")
    public ResponseEntity<ApiResponse> listAllPostsWithOutLogin(@ModelAttribute PagingCriteria pagingCriteria) {
        List<BoardVO> boardVOList = boardService.listPosts(pagingCriteria, "ALL");

        return new ResponseEntity<>(ApiResponse.success(boardVOList), HttpStatus.OK);
    }

    @Authorization
    @GetMapping
    public ResponseEntity<ApiResponse> listAllPost(@ModelAttribute PagingCriteria pagingCriteria, Member member) {

        List<BoardVO> boardVOList = boardService.listPosts(pagingCriteria, "ALL");

        return new ResponseEntity<>(ApiResponse.success(boardVOList), HttpStatus.OK);
    }

    @Authorization
    @GetMapping("/{mbti}")
    public ResponseEntity<ApiResponse> listAllWithMBTI(@PathVariable("mbti") String mbti, @ModelAttribute PagingCriteria pagingCriteria, Member member) {

        List<BoardVO> boardVOList = boardService.listPosts(pagingCriteria, mbti);

        return new ResponseEntity<>(ApiResponse.success(boardVOList), HttpStatus.OK);
    }

    @Authorization
    @GetMapping("/myPosts")
    public ResponseEntity<ApiResponse> listMyPosts(@ModelAttribute PagingCriteria pagingCriteria, Member member) {
        List<BoardVO> boardVOList = boardService.listPosts(pagingCriteria, member);
        return new ResponseEntity<>(ApiResponse.success(boardVOList), HttpStatus.OK);
    }

    @Authorization
    @GetMapping("/myComment")
    public ResponseEntity<ApiResponse> listMyCommentPosts(@ModelAttribute PagingCriteria pagingCriteria, Member member) {
        List<BoardVO> boardVOList = boardService.listCommentPosts(pagingCriteria, member);
        return new ResponseEntity<>(ApiResponse.success(boardVOList), HttpStatus.OK);
    }

    @Authorization
    @GetMapping("/myLike")
    public ResponseEntity<ApiResponse> listMyLikePosts(@ModelAttribute PagingCriteria pagingCriteria, Member member) {
        List<BoardVO> boardVOList = boardService.listLikePosts(pagingCriteria, member);
        return new ResponseEntity<>(ApiResponse.success(boardVOList), HttpStatus.OK);
    }

    @Authorization
    @GetMapping("/hot")
    public ResponseEntity<ApiResponse> listHotPosts(@ModelAttribute PagingCriteria pagingCriteria, Member member) {
        List<BoardVO> boardVOList = boardService.listHotPosts(pagingCriteria);
        return new ResponseEntity<>(ApiResponse.success(boardVOList), HttpStatus.OK);
    }

    @Authorization
    @GetMapping("/{mbti}/hot")
    public ResponseEntity<ApiResponse> listHotPostsInMbti(@PathVariable String mbti, @ModelAttribute PagingCriteria pagingCriteria, Member member) {
        List<BoardVO> boardVOList = boardService.listHotPostsWithMbti(pagingCriteria, mbti);
        return new ResponseEntity<>(ApiResponse.success(boardVOList), HttpStatus.OK);
    }
}
