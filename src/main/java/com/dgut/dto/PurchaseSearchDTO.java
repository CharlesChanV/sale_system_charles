package com.dgut.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;

@Data
public class PurchaseSearchDTO {
    private Byte payStatus=-1;
    private Integer deliverStatus=-1;
    private Integer contractId=-1;
    private Integer logisticsId=-1;
    private Integer customerId=-1;
    private String userId;
    private Integer adminId=-1;
}
