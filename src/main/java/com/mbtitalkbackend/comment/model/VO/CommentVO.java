package com.mbtitalkbackend.comment.model.VO;

import com.mbtitalkbackend.comment.model.entity.CommentEntity;
import com.mbtitalkbackend.member.model.entity.MemberEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentVO {

    private String commentId;
    private String postId;
    private String recommentId;
    private String content;
    private long likeCount;
    private Timestamp createTime;

    //member Info
    private String memberId;
    private String mbti;
    private String nickname;

    public static CommentVO of(CommentEntity commentEntity, MemberEntity memberEntity) {
        CommentVO commentVO = new CommentVO();

        commentVO.commentId = commentEntity.getCommentId();
        commentVO.postId = commentEntity.getPostId();
        commentVO.content = commentEntity.getContent();
        commentVO.likeCount = commentEntity.getLikeCount();
        commentVO.createTime = commentEntity.getCreateTime();

        commentVO.memberId = memberEntity.getMemberId();
        commentVO.mbti = memberEntity.getMbti();
        commentVO.nickname = memberEntity.getNickname();

        return commentVO;
    }
}
