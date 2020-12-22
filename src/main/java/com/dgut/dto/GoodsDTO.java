package com.dgut.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;

public class GoodsDTO {
    private Integer goodsId;
    private String name;
    private String description;
    private Double perPrice;
    private Integer stock;
    private Byte status;
}
