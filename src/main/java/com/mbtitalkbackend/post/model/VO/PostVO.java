package com.mbtitalkbackend.post.model.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostVO {

    private int board_id;
    private int member_id;
    private String title;
    private String content;
}
