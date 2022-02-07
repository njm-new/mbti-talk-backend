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
        List<PostEntity> postEntityList = boardMapper.findAllPostsWithPaging(pagingCriteria);

        return generateList(postEntityList);
    }

    // boardId로 검색 mbti or ALL
    public List<BoardVO> listPosts(PagingCriteria pagingCriteria, String boardId) {
        List<PostEntity> postEntityList = boardMapper.findAllPostsWithBoardId(pagingCriteria, boardId);

        return generateList(postEntityList);
    }

    // memberId로 검색, 내 게시글
    public List<BoardVO> listPosts(PagingCriteria pagingCriteria, Member member) {
        List<PostEntity> postEntityList = boardMapper.findMyPosts(pagingCriteria, member.getMemberId());

        return generateList(postEntityList);
    }

    // 내가 댓글 쓴 게시글
    public List<BoardVO> listCommentPosts(PagingCriteria pagingCriteria, Member member) {
        List<CommentEntity> commentEntityList = commentMapper.findCommentListByMemberId(member.getMemberId());
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
        List<PostEntity> postEntityList = boardMapper.findHotPostsWithBoardId(pagingCriteria, "ALL");

        return generateList(postEntityList);
    }

    // mbti 핫 게시글
    public List<BoardVO> listHotPostsWithMbti(PagingCriteria pagingCriteria, String mbti) {
        List<PostEntity> postEntityList = boardMapper.findHotPostsWithBoardId(pagingCriteria, mbti);

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
