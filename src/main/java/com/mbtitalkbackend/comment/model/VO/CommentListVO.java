package com.mbtitalkbackend.comment.model.VO;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentListVO {

    private int commentCount;
    private List<CommentVO> commentList;

    private CommentListVO (int commentCount, List<CommentVO> commentVOList) {
        this.commentCount = commentCount;
        this.commentList = commentVOList;
    }

    public static CommentListVO create(int commentCount, List<CommentVO> commentList) {
        return new CommentListVO(commentCount, commentList);
    }
}
