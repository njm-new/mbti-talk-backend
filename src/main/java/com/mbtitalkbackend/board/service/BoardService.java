package com.mbtitalkbackend.board.service;

import com.mbtitalkbackend.board.mapper.BoardMapper;
import com.mbtitalkbackend.board.model.PagingCriteria;
import com.mbtitalkbackend.board.model.VO.BoardVO;
import com.mbtitalkbackend.comment.mapper.CommentMapper;
import com.mbtitalkbackend.comment.model.entity.CommentEntity;
import com.mbtitalkbackend.like.mapper.LikeMapper;
import com.mbtitalkbackend.like.model.entity.LikeEntity;
import com.mbtitalkbackend.member.mapper.MemberMapper;
import com.mbtitalkbackend.member.model.entity.MemberEntity;
import com.mbtitalkbackend.member.model.vo.Member;
import com.mbtitalkbackend.post.mapper.PostMapper;
import com.mbtitalkbackend.post.model.Entity.PostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;
    private final MemberMapper memberMapper;
    private final CommentMapper commentMapper;
    private final PostMapper postMapper;
    private final LikeMapper likeMapper;

    public List<PostEntity> listAllPosts() {

        return boardMapper.findAllPosts();
    }

    public List<BoardVO> listPosts(PagingCriteria pagingCriteria) {
        int pageNum = pagingCriteria.getPageNum();
        int rowPerPage = pagingCriteria.getRowPerPage();
        int offset = pageNum * rowPerPage;

        List<PostEntity> postEntityList = boardMapper.findAllPostsWithPaging(offset, rowPerPage);

        return generateList(postEntityList);
    }

    // boardId로 검색 mbti or ALL
    public List<BoardVO> listPosts(PagingCriteria pagingCriteria, String boardId) {
        int pageNum = pagingCriteria.getPageNum();
        int rowPerPage = pagingCriteria.getRowPerPage();
        int offset = pageNum * rowPerPage;

        List<PostEntity> postEntityList = boardMapper.findAllPostsWithBoardId(offset, rowPerPage, boardId);

        return generateList(postEntityList);
    }

    // memberId로 검색, 내 게시글
    public List<BoardVO> listPosts(PagingCriteria pagingCriteria, Member member) {
        int pageNum = pagingCriteria.getPageNum();
        int rowPerPage = pagingCriteria.getRowPerPage();
        int offset = pageNum * rowPerPage;

        List<PostEntity> postEntityList = boardMapper.findMyPosts(offset, rowPerPage, member.getMemberId());

        return generateList(postEntityList);
    }

    // 내가 댓글 쓴 게시글
    public List<BoardVO> listCommentPosts(PagingCriteria pagingCriteria, Member member) {
        List<CommentEntity> commentEntityList = commentMapper.findCommentListByMemberId(member.getMemberId());
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>();

        for (CommentEntity commentEntity : commentEntityList) {
            System.out.println(commentEntity);
            linkedHashSet.add(commentEntity.getPostId());
        }

        List<PostEntity> postEntityList = new ArrayList<>();

        for (String postId : linkedHashSet) {
            System.out.println(postId);
            postEntityList.add(postMapper.findPostEntityByPostId(postId));
        }


        return generateList(postEntityList);
    }

    // 내가 좋아요한 게시글
    public List<BoardVO> listLikePosts(PagingCriteria pagingCriteria, Member member) {
        List<LikeEntity> likeEntityList = likeMapper.findLikeByMemberId(member.getMemberId());
        List<PostEntity> postEntityList = new ArrayList<>();

        for (LikeEntity likeEntity : likeEntityList) {
            postEntityList.add(postMapper.findPostEntityByPostId(likeEntity.getPostId()));
        }

        return generateList(postEntityList);
    }

    // 전체 핫 게시글
    public List<BoardVO> listHotPosts(PagingCriteria pagingCriteria) {
        int pageNum = pagingCriteria.getPageNum();
        int rowPerPage = pagingCriteria.getRowPerPage();
        int offset = pageNum * rowPerPage;

        List<PostEntity> postEntityList = boardMapper.findHotPostsWithBoardId(offset, rowPerPage, "ALL");

        return generateList(postEntityList);
    }

    // mbti 핫 게시글
    public List<BoardVO> listHotPostsWithMbti(PagingCriteria pagingCriteria, String mbti) {
        int pageNum = pagingCriteria.getPageNum();
        int rowPerPage = pagingCriteria.getRowPerPage();
        int offset = pageNum * rowPerPage;

        List<PostEntity> postEntityList = boardMapper.findHotPostsWithBoardId(offset, rowPerPage, mbti);

        return generateList(postEntityList);
    }

    public List<BoardVO> generateList(List<PostEntity> postEntityList) {

        List<BoardVO> boardVOList = new ArrayList<>();
        for (PostEntity postEntity : postEntityList) {
            MemberEntity memberEntity = memberMapper.findMemberById(postEntity.getMemberId());
            int commentCount = commentMapper.countCommentByPostId(postEntity.getPostId());
            boardVOList.add(BoardVO.of(postEntity, memberEntity, commentCount));
        }

        return boardVOList;
    }

    public List<BoardVO> checkLike(List<BoardVO> boardVOList, Member member) {

        for(BoardVO boardVO : boardVOList) {
            boardVO.setLike((likeMapper.findLike(LikeEntity.create(boardVO.getPostId(), member.getMemberId())) != null));
        }

        return boardVOList;
    }
}
