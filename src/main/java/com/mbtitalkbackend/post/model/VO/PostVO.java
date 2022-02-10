package com.mbtitalkbackend.post.model.VO;

import com.mbtitalkbackend.member.model.entity.MemberEntity;
import com.mbtitalkbackend.post.model.Entity.PostEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostVO {

    private String postId;
    private String boardId;
    private String title;
    private String content;
    private long viewCount;
    private long likeCount;
    private Timestamp createTime;

    //member Info
    private String memberId;
    private String mbti;
    private String nickname;

    private boolean like;

    public static PostVO of(PostEntity postEntity, MemberEntity memberEntity, boolean like) { //멤버 엔티티 추가 후 포스트에 벰버 정보 같이 담아 보내기용
        PostVO postVO = new PostVO();

        postVO.postId = postEntity.getPostId();
        postVO.boardId = postEntity.getBoardId();
        postVO.title = postEntity.getTitle();
        postVO.content = postEntity.getContent();
        postVO.viewCount = postEntity.getViewCount();
        postVO.likeCount = postEntity.getLikeCount();
        postVO.createTime = postEntity.getCreateTime();

        postVO.memberId = postEntity.getMemberId();
        postVO.mbti = memberEntity.getMbti();
        postVO.nickname = memberEntity.getNickname();

        postVO.like = like;

        return postVO;
    }
}
