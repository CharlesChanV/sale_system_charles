package com.dgut.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PurchaseItemUpdateListDTO {
    private Integer purchaseItemId = -1;
    private Integer count = -1;
    private Integer goodsId = -1;
}
