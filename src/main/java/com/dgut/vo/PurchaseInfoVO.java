package com.dgut.vo;

import com.dgut.entity.PurchaseItemEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.util.List;

@Data
public class PurchaseInfoVO {
    private int purchaseId;
    private byte payStatus;
    private int totalPrice;
    private int deliverStatus;
    private int contractId;
    private int logisticsId;
    private int customerId;
    private int userId;
    private int adminId;
    private List<PurchaseItemEntity> purchaseItemList;
}
