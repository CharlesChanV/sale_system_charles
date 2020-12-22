package com.dgut.dto;

import lombok.Data;

@Data
public class GoodsInDTO {
    private Integer goodsInId;
    private Integer goodsId;
    private Integer count;
    private Byte status;
    private Integer adminId;
}
