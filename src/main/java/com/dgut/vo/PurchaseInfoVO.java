package com.dgut.vo;

import com.dgut.entity.PurchaseItemEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

@Data
public class PurchaseInfoVO {
    private Integer purchaseId;
    private String purchaseNo;
    private byte payStatus;
    private Integer totalPrice;
    private Integer deliverStatus;
    private Integer contractId;
    private Integer logisticsId;
    private Integer customerId;
    private String userId;
    private Integer adminId;
    private Date createTime;
    private List<PurchaseItemEntity> purchaseItemList;
}
