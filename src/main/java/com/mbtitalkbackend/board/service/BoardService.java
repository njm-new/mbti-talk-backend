package com.mbtitalkbackend.board.service;

import com.mbtitalkbackend.board.mapper.BoardMapper;
import com.mbtitalkbackend.board.model.PagingCriteria;
import com.mbtitalkbackend.board.model.VO.BoardVO;
import com.mbtitalkbackend.comment.mapper.CommentMapper;
import com.mbtitalkbackend.comment.model.entity.CommentEntity;
import com.mbtitalkbackend.member.mapper.MemberMapper;
import com.mbtitalkbackend.member.model.entity.MemberEntity;
import com.mbtitalkbackend.post.model.Entity.PostEntity;
import com.mbtitalkbackend.post.model.VO.PostVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;
    private final MemberMapper memberMapper;
    private final CommentMapper commentMapper;

    public List<PostEntity> listAllPosts() {

        return boardMapper.findAllPosts();
    }

    public List<BoardVO> listAllPostsWithPaging(PagingCriteria pagingCriteria) {

        List<PostEntity> postEntityList = boardMapper.findAllPostsWithPaging(pagingCriteria);

        return generateList(postEntityList);
    }

    public List<BoardVO> listAllPostsWithMBTI(PagingCriteria pagingCriteria, String mbti) {

        List<PostEntity> postEntityList = boardMapper.findAllPostsWithMBTI(pagingCriteria, mbti);

        return generateList(postEntityList);
    }

    public List<BoardVO> listPosts(PagingCriteria pagingCriteria) {
        List<PostEntity> postEntityList = boardMapper.findAllPostsWithPaging(pagingCriteria);

        return generateList(postEntityList);
    }

    public List<BoardVO> listPosts(PagingCriteria pagingCriteria, String mbti) {
        List<PostEntity> postEntityList = boardMapper.findAllPostsWithMBTI(pagingCriteria, mbti);

        return generateList(postEntityList);
    }

    public List<BoardVO> generateList(List<PostEntity> postEntityList) {

        List<BoardVO> boardVOList = new ArrayList<>();

        for (PostEntity postEntity : postEntityList) {
            MemberEntity memberEntity = memberMapper.findMemberById(postEntity.getMemberId());
            int commentCount = commentMapper.countCommentByCommentId(postEntity.getPostId());
            boardVOList.add(BoardVO.of(postEntity, memberEntity, commentCount));
        }

        return boardVOList;
    }
}
