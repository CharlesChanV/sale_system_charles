package com.dgut.service;

import com.dgut.entity.ContractItemEntity;

import java.util.List;

public interface ContractItemService {
    public List<ContractItemEntity> getContractItemListByContractId(Integer contractId);

    /**
     * 更新合同商品数据
     * @param contractItemEntityList
     * @return
     */
    public int updateListByContractItemId(List<ContractItemEntity> contractItemEntityList);

    /**
     * 更新合同商品数据列表[合同未履行]
     * @param contractId
     * @param contractItemEntityList
     * @return
     */
    public int modifyContractItemList(Integer contractId, List<ContractItemEntity> contractItemEntityList);

    /**
     * 删除合同-商品数据列表
     * @param contractId
     * @param contractItemEntityList
     * @return
     */
    public int deleteContractItemList(Integer contractId, List<Integer> contractItemEntityList);
    /**
     * 新增合同-商品数据列表
     * @param contractId
     * @param contractItemEntityList
     * @return
     */
    public int saveContractItemList(Integer contractId, List<ContractItemEntity> contractItemEntityList);
}
