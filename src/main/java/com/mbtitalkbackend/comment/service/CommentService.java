package com.mbtitalkbackend.comment.service;

import com.mbtitalkbackend.comment.mapper.CommentMapper;
import com.mbtitalkbackend.comment.model.VO.CommentVOList;
import com.mbtitalkbackend.comment.model.VO.CommentVO;
import com.mbtitalkbackend.comment.model.entity.CommentEntity;
import com.mbtitalkbackend.member.mapper.MemberMapper;
import com.mbtitalkbackend.member.model.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentMapper commentMapper;
    private final MemberMapper memberMapper;

    public Integer createComment(CommentVO commentVO) {

        CommentEntity commentEntity = CommentEntity.create(commentVO);

        return commentMapper.createComment(commentEntity);
    }

    public CommentVO findCommentByCommentId(String commentId) {

        try {
            CommentEntity commentEntity = commentMapper.findCommentByCommentId(commentId);
            MemberEntity memberEntity = memberMapper.findMemberById(commentEntity.getMemberId());

            return CommentVO.of(commentEntity, memberEntity);
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }
    }

    public CommentVOList findCommentList(String postId) {

        List<CommentEntity> commentEntityList = commentMapper.findCommentListByPostId(postId);

        int commentCount = commentEntityList.size();

        List<CommentVO> commentList = new ArrayList<>();

        for (CommentEntity commentEntity : commentEntityList) {
            MemberEntity memberEntity = memberMapper.findMemberById(commentEntity.getMemberId());

            commentList.add(CommentVO.of(commentEntity, memberEntity));
        }

        return CommentVOList.create(commentCount, commentList);
    }

    public Integer updateComment(CommentVO commentVO) {

        try {
            commentMapper.updateCommentModifiedTime(commentVO.getCommentId());
            CommentEntity commentEntity = commentMapper.findCommentByCommentId(commentVO.getCommentId());

//            commentEntity.setMemberId(commentVO.getMemberId());
            commentEntity.setContent(commentVO.getContent());

            return commentMapper.updateComment(commentEntity);
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }

    }

    public Integer deleteCommentByCommentId(String commentId) {

        return commentMapper.deleteCommentByCommentId(commentId);

    }
}
