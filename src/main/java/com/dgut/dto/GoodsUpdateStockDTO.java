package com.dgut.dto;

import com.dgut.entity.PurchaseItemEntity;
import org.springframework.beans.BeanUtils;

import java.util.Optional;

public class GoodsUpdateStockDTO {
    private Integer goodsId;
    private Integer stock;
    public GoodsUpdateStockDTO(PurchaseItemEntity purchaseItemEntity) {
        Optional.ofNullable(purchaseItemEntity).ifPresent(item->{
            BeanUtils.copyProperties(item,this);
            this.stock = item.getCount();
        });
    }
}
