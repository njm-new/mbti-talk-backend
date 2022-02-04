package com.mbtitalkbackend.board.model.VO;

import com.mbtitalkbackend.board.model.PagingCriteria;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestVO {

    private int memberId;
    private PagingCriteria pagingCriteria;
}
