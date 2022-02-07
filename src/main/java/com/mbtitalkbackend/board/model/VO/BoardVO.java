package com.mbtitalkbackend.board.model.VO;

import com.mbtitalkbackend.member.model.entity.MemberEntity;
import com.mbtitalkbackend.post.model.Entity.PostEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardVO {

    // post Info
    private long postId;
    private String boardId;
    private String title;
    private String content;
    private long viewCount;
    private long likeCount;
    private Timestamp createTime;

    // member Info
    private int memberId;
    private String mbti;
    private String nickname;

    // Comment Info
    private int commentCount;

    // like Info
    private boolean like;

    public static BoardVO of(PostEntity postEntity, MemberEntity memberEntity, int commentCount) {
        BoardVO boardVO = new BoardVO();

        boardVO.postId = postEntity.getPostId();
        boardVO.boardId = postEntity.getBoardId();
        boardVO.title = postEntity.getTitle();
        boardVO.content = postEntity.getContent();
        boardVO.viewCount = postEntity.getViewCount();
        boardVO.likeCount = postEntity.getLikeCount();
        boardVO.createTime = postEntity.getCreateTime();

        boardVO.memberId = memberEntity.getMemberId();
        boardVO.mbti = memberEntity.getMbti();
        boardVO.nickname = memberEntity.getNickname();

        boardVO.commentCount = commentCount;

        return boardVO;
    }
}
