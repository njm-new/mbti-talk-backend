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

    private PostEntity(PostVO postVO, long viewCount, long likeCount) {
        this.boardId = postVO.getBoardId();
        this.memberId = postVO.getMemberId();
        this.title = postVO.getTitle();
        this.content = postVO.getContent();
        this.viewCount = viewCount;
        this.likeCount = likeCount;
    }

    public static PostEntity create(PostVO postVO) {

        return new PostEntity(postVO, 0, 0);
    }
}
