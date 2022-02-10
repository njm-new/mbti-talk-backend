package com.mbtitalkbackend.comment.model.entity;

import com.mbtitalkbackend.comment.model.VO.CommentVO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentEntity {

    private String commentId;
    private String postId;
    private String recommentId;
    private String memberId;
    private String title;
    private String content;
    private long likeCount;
    private Timestamp createTime;
    private Timestamp modifiedTime;

    private CommentEntity(CommentVO commentVO, long likeCount) {
        this.commentId = commentVO.getCommentId();
        this.postId = commentVO.getPostId();
        this.memberId = commentVO.getMemberId();
        this.content = commentVO.getContent();
        this.likeCount = likeCount;
    }

    public static CommentEntity create(CommentVO commentVO) {

        return new CommentEntity(commentVO, 0);
    }
}
