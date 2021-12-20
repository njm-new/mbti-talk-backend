package com.mbtitalkbackend.post.model.Entity;

import com.mbtitalkbackend.post.model.VO.PostVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostEntity {

    private long post_id;
    private int board_id;
    private String member_id;
    private String title;
    private String content;
    private long view_count;
    private long like_count;
    private Timestamp create_time;
    private Timestamp modified_time;

    public PostEntity(int board_id, String member_id, String title, String content, long view_count, long like_count) {
        this.board_id = board_id;
        this.member_id = member_id;
        this.title = title;
        this.content = content;
        this.view_count = view_count;
        this.like_count = like_count;
    }

    public PostEntity(int post_id, PostVO postVO) {
        this.post_id = post_id;
        this.board_id = postVO.getBoard_id();
        this.member_id = postVO.getMember_id();
        this.title = postVO.getTitle();
        this.content = postVO.getContent();
    }
}
