package com.dgut.service;

import com.dgut.entity.PurchaseEntity;
import com.dgut.entity.PurchaseItemEntity;

import java.util.List;

public interface PurchaseItemService {

    public int updatePurchaseItemList(Integer purchaseId, List<PurchaseItemEntity> purchaseItemEntityList) throws Exception;

    public int deletePurchaseItemList(Integer purchaseId, List<PurchaseItemEntity> purchaseItemEntityList) throws Exception;

    public int savePurchaseItemList(PurchaseEntity purchase, List<PurchaseItemEntity> purchaseItemEntityList) throws Exception;
}
