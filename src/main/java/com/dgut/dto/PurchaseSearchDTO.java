package com.dgut.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;

@Data
public class PurchaseSearchDTO {
    private Byte payStatus;
    private Integer deliverStatus;
    private Integer contractId;
    private Integer logisticsId;
    private Integer customerId;
    private String userId;
    private Integer adminId;
}
