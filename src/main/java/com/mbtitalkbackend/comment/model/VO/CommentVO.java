package com.mbtitalkbackend.comment.model.VO;

import com.mbtitalkbackend.comment.model.entity.CommentEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentVO {

    private long commentId;
    private long postId;
    private long recommentId;
    private String title;
    private String content;
    private long likeCount;
    private Timestamp createTime;

    //member Info
    private long memberId;
    private String mbti;
    private String nickname;

    public static CommentVO of(CommentEntity commentEntity /*, MemberEntity memberEntity*/) {
        CommentVO commentVO = new CommentVO();

        commentVO.commentId = commentEntity.getCommentId();
        commentVO.postId = commentEntity.getPostId();
        commentVO.content = commentEntity.getContent();
        commentVO.likeCount = commentEntity.getLikeCount();
        commentVO.createTime = commentEntity.getCreateTime();

        commentVO.memberId = commentEntity.getMemberId();
        //commentVO.mbti = commentEntity.getMbti();
        //commentVO.nickname = commentEntity.getNickname();

        return commentVO;
    }
}
