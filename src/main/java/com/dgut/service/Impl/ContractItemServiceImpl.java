package com.dgut.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dgut.entity.ContractItemEntity;
import com.dgut.entity.GoodsEntity;
import com.dgut.mapper.ContractItemMapper;
import com.dgut.mapper.ContractMapper;
import com.dgut.mapper.GoodsMapper;
import com.dgut.service.ContractItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContractItemServiceImpl extends ServiceImpl<ContractItemMapper, ContractItemEntity> implements ContractItemService {

    @Autowired
    ContractMapper contractMapper;

    @Autowired
    GoodsMapper goodsMapper;

    public List<ContractItemEntity> getContractItemListByContractId(Integer contractId) {
        return baseMapper.selectList(new QueryWrapper<ContractItemEntity>().eq("contract_id",contractId));
    }
    public int updateListByContractItemId(List<ContractItemEntity> contractItemEntityList) {
        return baseMapper.updateListByContractItemId(contractItemEntityList);
    }
    public int modifyContractItemList(Integer contractId, List<ContractItemEntity> contractItemEntityList){
        Optional.ofNullable(contractMapper.selectById(contractId)).ifPresent(item->{
            if(item.getStatus()>0) {
                throw new RuntimeException("当前合同状态不可以进行此操作");
            }
            if(this.updateListByContractItemId(contractItemEntityList)==0){
                throw new RuntimeException("更新合同-商品数据异常");
            }
        });
        return 1;
    }
    public int deleteContractItemList(Integer contractId, List<Integer> contractItemEntityList) {
        Optional.ofNullable(contractMapper.selectById(contractId)).ifPresent(item->{
            if(item.getStatus()>0) {
                throw new RuntimeException("当前合同状态不可以进行此操作");
            }
            if(baseMapper.deleteBatchIds(contractItemEntityList)==0){
                throw new RuntimeException("删除合同-商品数据异常");
            }
        });
        return 1;
    }
    public int saveContractItemList(Integer contractId, List<ContractItemEntity> contractItemEntityList) {
        Optional.ofNullable(contractMapper.selectById(contractId)).ifPresent(item->{
            if(item.getStatus()>0) {
                throw new RuntimeException("当前合同状态不可以进行此操作");
            }
//            if(baseMapper.deleteBatchIds(contractItemEntityList.stream().map(ContractItemEntity::getContractItemId).collect(Collectors.toList()))==0){
//                throw new RuntimeException("删除合同-商品数据异常");
//            }
        });
        List<Integer> GoodsIdCollect = contractItemEntityList.stream().map(ContractItemEntity::getGoodsId).collect(Collectors.toList());
        List<GoodsEntity> goodsEntities = goodsMapper.selectBatchIds(GoodsIdCollect);
        Map<Integer, GoodsEntity> goodsEntityMap = new HashMap<>();
        List<Object> collect = goodsEntities.stream().map(item -> {
            goodsEntityMap.put(item.getGoodsId(), item);
            return item;
        }).collect(Collectors.toList());
        List<Object> collect1 = contractItemEntityList.stream().map(item -> {
            if (goodsEntityMap.containsKey(item.getGoodsId())) {
                GoodsEntity goodsEntity = goodsEntityMap.get(item.getGoodsId());
                item.setPerPrice(goodsEntity.getPerPrice());
                item.setLeaveCount(item.getCount());
                item.setContractId(contractId);
//                item.setPrice(goodsEntity.getPerPrice() * item.getCount());
            }
            return item;
        }).collect(Collectors.toList());
        return baseMapper.insertBatch(contractItemEntityList);
    }
}
