package com.dgut.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PurchaseItemInfoVO {
    private Integer purchaseItemId;
    private Integer count;
    private Double perPrice;
    private Double price;
    private Integer purchaseId;
    private Integer goodsId;
    private String name;
}
