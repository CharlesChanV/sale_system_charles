package com.dgut.vo;

import com.dgut.entity.PurchaseEntity;
import com.dgut.entity.PurchaseItemEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import java.util.List;
import java.util.Optional;

/**
 * 获取采购清单携带数据项[单一数据][商品数据]
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PurchaseWithItemVO extends BaseVO {
    private int purchaseId;
    private byte payStatus;
    private int totalPrice;
    private int deliverStatus;
    private int contractId;
    private int logisticsId;
    private int customerId;
    private int userId;
    private int adminId;
    private List<PurchaseItemInfoVO> purchaseItemEntityList;
    public PurchaseWithItemVO() {}
    public PurchaseWithItemVO(PurchaseEntity purchaseEntity, List<PurchaseItemEntity> purchaseItemEntityList) {
        Optional.ofNullable(purchaseEntity).ifPresent(item->{
            BeanUtils.copyProperties(item,this);
        });
        BeanUtils.copyProperties(purchaseItemEntityList,this.purchaseItemEntityList);
    }
}
