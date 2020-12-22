package com.dgut.vo;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {

    private long total;

    private Integer code = 0;

    private List<T> data;

//    private boolean first;
//
//    private boolean last;

//    private int totalPages;

    private Long current;

    private Long pages;

    private Long size;
}
