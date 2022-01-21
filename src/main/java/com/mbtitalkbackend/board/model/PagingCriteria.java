package com.mbtitalkbackend.board.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PagingCriteria {

    private int pageNum; //
    private int rowPerPage = 12; //  default page amount = 12

    public PagingCriteria() {
        this(1, 12);
    }

    public PagingCriteria(int pageNum, int rowPerPage) {
        this.pageNum = pageNum;
        this.rowPerPage = rowPerPage;
    }
}
