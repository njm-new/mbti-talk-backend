package com.mbtitalkbackend.board.service;

import com.mbtitalkbackend.board.mapper.BoardMapper;
import com.mbtitalkbackend.board.model.PagingCriteria;
import com.mbtitalkbackend.board.model.VO.BoardVO;
import com.mbtitalkbackend.board.model.VO.RequestVO;
import com.mbtitalkbackend.comment.mapper.CommentMapper;
import com.mbtitalkbackend.comment.model.entity.CommentEntity;
import com.mbtitalkbackend.like.mapper.LikeMapper;
import com.mbtitalkbackend.like.model.entity.LikeEntity;
import com.mbtitalkbackend.member.mapper.MemberMapper;
import com.mbtitalkbackend.member.model.entity.MemberEntity;
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


    // 1안 이름 구별이 쉽게
    public List<BoardVO> listAllPostsWithPaging(PagingCriteria pagingCriteria) {

        List<PostEntity> postEntityList = boardMapper.findAllPostsWithPaging(pagingCriteria);

        return generateList(postEntityList);
    }

    public List<BoardVO> listAllPostsWithMBTI(PagingCriteria pagingCriteria, String mbti) {

        List<PostEntity> postEntityList = boardMapper.findAllPostsWithMBTI(pagingCriteria, mbti);

        return generateList(postEntityList);
    }


    // 2안 메소드 오버로딩
    public List<BoardVO> listPosts(PagingCriteria pagingCriteria) {
        List<PostEntity> postEntityList = boardMapper.findAllPostsWithPaging(pagingCriteria);

        return generateList(postEntityList);
    }

    public List<BoardVO> listPosts(PagingCriteria pagingCriteria, String mbti) {
        List<PostEntity> postEntityList = boardMapper.findAllPostsWithMBTI(pagingCriteria, mbti);

        return generateList(postEntityList);
    }

    public List<BoardVO> listPosts(PagingCriteria pagingCriteria, long memberId) {
        List<PostEntity> postEntityList = boardMapper.findMyPosts(pagingCriteria, memberId);

        return generateList(postEntityList);
    }


    // 3안 객체로 받아 분기문으로 처리
    public List<BoardVO> listPosts(RequestVO requestVO) {

        List<PostEntity> postEntityList;

        if(requestVO.getMemberId() == 0)
            postEntityList = boardMapper.findAllPostsWithPaging(requestVO.getPagingCriteria());
        else
            postEntityList = boardMapper.findMyPosts(requestVO.getPagingCriteria(), requestVO.getMemberId());

        return generateList(postEntityList);
    }


    public List<BoardVO> listCommentPosts(RequestVO requestVO) {
        List<CommentEntity> commentEntityList = commentMapper.findCommentListByMemberId(requestVO.getMemberId());
        LinkedHashSet<Long> linkedHashSet = new LinkedHashSet<>();

        for (CommentEntity commentEntity : commentEntityList) {
            linkedHashSet.add(commentEntity.getPostId());
        }

        List<PostEntity> postEntityList = new ArrayList<>();

        for (Long postId : linkedHashSet) {
            postEntityList.add(postMapper.findPostEntityByPostId(postId));
        }

        return generateList(postEntityList);
    }

    public List<BoardVO> listLikePosts(RequestVO requestVO) {
        List<LikeEntity> likeEntityList = likeMapper.findLikeByMemberId(requestVO.getMemberId());
        List<PostEntity> postEntityList = new ArrayList<>();

        for (LikeEntity likeEntity : likeEntityList) {
            postEntityList.add(postMapper.findPostEntityByPostId(likeEntity.getPostId()));
        }

        return generateList(postEntityList);
    }

    public List<BoardVO> listHotPosts(RequestVO requestVO) {
        List<PostEntity> postEntityList = boardMapper.findHotPosts(requestVO.getPagingCriteria());

        return generateList(postEntityList);
    }

    public List<BoardVO> listHotPostsWithMbti(RequestVO requestVO, String mbti) {
        List<PostEntity> postEntityList = boardMapper.findHotPostsWithMBTI(requestVO.getPagingCriteria(), mbti);

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
}
