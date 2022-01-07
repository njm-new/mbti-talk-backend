package com.mbtitalkbackend.comment.service;

import com.mbtitalkbackend.comment.mapper.CommentMapper;
import com.mbtitalkbackend.comment.model.VO.CommentListVO;
import com.mbtitalkbackend.comment.model.VO.CommentVO;
import com.mbtitalkbackend.comment.model.entity.CommentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentMapper commentMapper;

    public Integer createComment(CommentVO commentVO) {

        CommentEntity commentEntity = CommentEntity.create(commentVO);

        return commentMapper.createComment(commentEntity);
    }

    public CommentVO findCommentByCommentId(long commentId) {

        try {
            CommentEntity commentEntity = commentMapper.findCommentByCommentId(commentId);

            return CommentVO.of(commentEntity);
        }
        catch (NullPointerException e) {
            throw new NullPointerException();
        }
    }

    public CommentListVO findCommentList(long postId) {

        List<CommentEntity> commentEntityList = commentMapper.findCommentList(postId);

        int commentCount = commentMapper.countCommentByCommentId(postId);

        List<CommentVO> commentVOList = new ArrayList<>();

        for (CommentEntity commentEntity : commentEntityList) {
            commentVOList.add(CommentVO.of(commentEntity));
        }

        return CommentListVO.create(commentCount, commentVOList);
    }

    public Integer updateComment(long commentId, CommentVO commentVO) {

        try {
            commentMapper.updateCommentModifiedTime(commentId);
            CommentEntity commentEntity = commentMapper.findCommentByCommentId(commentId);

//            commentEntity.setMemberId(commentVO.getMemberId());
            commentEntity.setContent(commentVO.getContent());

            return commentMapper.updateComment(commentEntity);
        }
        catch (NullPointerException e) {
            throw new NullPointerException();
        }

    }

    public Integer deleteCommentByCommentId(long commentId) {

        return commentMapper.deleteCommentByCommentId(commentId);

    }
}
