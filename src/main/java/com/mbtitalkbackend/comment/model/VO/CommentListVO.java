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

    private CommentListVO (List<CommentVO> commentVOList) {
        this.commentCount = commentVOList.size();
        this.commentList = commentVOList;
    }

    public static CommentListVO create(List<CommentVO> commentList) {
        return new CommentListVO(commentList);
    }
}
