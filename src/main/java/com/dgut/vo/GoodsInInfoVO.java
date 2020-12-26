package com.dgut.vo;

import com.dgut.entity.LogisticsEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;

@Data
public class GoodsInInfoVO {
    private int goodsInId;
    private int goodsId;
    private int count;
    private byte status;
    private String remark;
    private int adminId;
    private int logisticsId;
    private String name;
    private LogisticsEntity logisticsInfo;
}
