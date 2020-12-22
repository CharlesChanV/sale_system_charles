package com.dgut.dto;

import lombok.Data;

@Data
public class PageDTO {
    private Integer page = 1;
    private Integer limit = 10;
}
