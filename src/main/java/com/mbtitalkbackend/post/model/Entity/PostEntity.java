package com.mbtitalkbackend.post.model.Entity;

import com.mbtitalkbackend.post.model.VO.PostVO;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostEntity {

    private String postId;
    private String boardId;
    private String memberId;
    private String title;
    private String content;
    private long viewCount;
    private long likeCount;
    private Timestamp createTime;
    private Timestamp modifiedTime;

    private PostEntity(PostVO postVO, String postId, long viewCount, long likeCount) {
        this.postId = postId;
        this.boardId = postVO.getBoardId();
        this.memberId = postVO.getMemberId();
        this.title = postVO.getTitle();
        this.content = postVO.getContent();
        this.viewCount = viewCount;
        this.likeCount = likeCount;
    }

    public static PostEntity create(PostVO postVO, String postId) {

        return new PostEntity(postVO, postId, 0, 0);
    }
}
