package com.dgut.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ContractItemVO {
    private Integer contractItemId;
    private Integer count;
    private Integer leaveCount;
    private Double perPrice;
    private Integer contractId;
    private Integer goodsId;
    private String name;
}
