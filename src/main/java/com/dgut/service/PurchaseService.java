package com.dgut.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dgut.entity.GoodsEntity;
import com.dgut.entity.LogisticsEntity;
import com.dgut.entity.PurchaseEntity;
import com.dgut.entity.PurchaseItemEntity;

import java.util.List;
import java.util.Map;

public interface PurchaseService extends IService<PurchaseEntity> {

    public PurchaseEntity savePurchase(PurchaseEntity purchaseEntity) throws Exception;

    /**
     * 删除清单信息[purchaseId]
     * @param purchaseId
     * @return
     * @throws Exception
     */
    public int deletePurchaseByPurchaseId(Integer purchaseId) throws Exception;

    /**
     * 恢复清单信息[purchaseId]
     * @param id
     * @return
     */
    public PurchaseEntity getPurchaseEntityById(Integer id);

    /**
     * 恢复商品库存信息
     * @param goodIdsList
     * @param purchaseItemList
     * @return
     */
    public int resetGoodsListByGoodsId(List<Integer> goodIdsList, Map<Integer, Integer> purchaseItemList) throws Exception;

    /**
     * 恢复合同-商品信息
     * @param contractId
     * @param purchaseItemList
     * @return
     * @throws Exception
     */
    public int resetContractItemListByContractId(Integer contractId, Map<Integer, Integer> purchaseItemList) throws Exception;

    /**
     * 更新采购清单信息
     * @param purchaseEntity
     * @return
     */
    public int updatePurchase(PurchaseEntity purchaseEntity) throws Exception;

    /**
     * 采购清单发货
     * @param purchaseId
     * @return
     * @throws Exception
     */
    public int deliverPurchase(Integer purchaseId, String senderUserId) throws Exception;
    /**
     * 处理封装物流信息
     * @param addresseeUserId
     * @param senderUserId
     * @return
     * @throws Exception
     */
    public LogisticsEntity handleLogisticsInfoNew(Integer addresseeUserId, String senderUserId) throws Exception;

}
