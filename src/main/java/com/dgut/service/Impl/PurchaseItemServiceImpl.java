package com.dgut.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dgut.entity.ContractItemEntity;
import com.dgut.entity.GoodsEntity;
import com.dgut.entity.PurchaseEntity;
import com.dgut.entity.PurchaseItemEntity;
import com.dgut.mapper.ContractItemMapper;
import com.dgut.mapper.GoodsMapper;
import com.dgut.mapper.PurchaseItemMapper;
import com.dgut.mapper.PurchaseMapper;
import com.dgut.service.PurchaseItemService;
import com.dgut.service.PurchaseService;
import com.dgut.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PurchaseItemServiceImpl extends ServiceImpl<PurchaseItemMapper, PurchaseItemEntity> implements PurchaseItemService {

    @Autowired
    PurchaseItemMapper purchaseItemMapper;

    @Autowired
    PurchaseMapper purchaseMapper;

    @Autowired
    PurchaseService purchaseService;

    @Autowired
    GoodsMapper goodsMapper;

    public int deletePurchaseItemList(Integer purchaseId, List<PurchaseItemEntity> purchaseItemEntityList) throws Exception {
        PurchaseEntity purchaseEntityById = purchaseService.getPurchaseEntityById(purchaseId);
        if(purchaseEntityById.getPayStatus() == 1) {
            throw new Exception("已支付无法删除");
        }
        List<Integer> purchaseItemIds = purchaseItemEntityList.stream().map(PurchaseItemEntity::getPurchaseItemId).collect(Collectors.toList());
        // 保存商品变动数据[商品ID-变动数值]
        Map<Integer, Integer> goodsIdChange = new HashMap<>();
        // 保存需要删除的清单商品数据ID
        List<Integer> deletedItem = new ArrayList<>();

        List<PurchaseItemEntity> purchaseItemEntities = purchaseItemMapper.selectList(new QueryWrapper<PurchaseItemEntity>().in("purchase_item_id", purchaseItemIds));
        List<PurchaseItemEntity> collect = purchaseItemEntities.stream().map(item -> {
            goodsIdChange.put(item.getGoodsId(), item.getCount());
            deletedItem.add(item.getPurchaseItemId());
            return item;
        }).collect(Collectors.toList());

        List<Integer> goodsIdList = new ArrayList<>();
        goodsIdChange.forEach((key,item)->{
            goodsIdList.add(key);
        });
        // 恢复合同-商品信息
        if(purchaseService.resetContractItemListByContractId(purchaseEntityById.getContractId(), goodsIdChange)<1) {
            throw new Exception("恢复合同-商品信息异常");
        }
        // 恢复商品库存信息
        if(purchaseService.resetGoodsListByGoodsId(goodsIdList,goodsIdChange)<1) {
            throw new Exception("恢复商品库存信息异常");
        }
        // 删除清单数据项
        System.out.println(deletedItem);
        if(purchaseItemMapper.deleteBatchIds(deletedItem)<1) {
            throw new Exception("删除清单中部分数据项异常");
        }
        return 1;

    }
    @Transactional
    public int updatePurchaseItemList(Integer purchaseId, List<PurchaseItemEntity> purchaseItemEntityList) throws Exception {
        PurchaseEntity purchaseEntityById = purchaseService.getPurchaseEntityById(purchaseId);
        if(purchaseEntityById.getPayStatus() == 1) {
            throw new Exception("已支付无法修改");
        }
        Map<Integer, PurchaseItemEntity> purchaseItemModify = new HashMap<>();
        List<Integer> purchaseItemIds = purchaseItemEntityList.stream().map(item->{
            purchaseItemModify.put(item.getPurchaseItemId(), item);
            return item.getPurchaseItemId();
        }).collect(Collectors.toList());
        // 保存商品变动数据[商品ID-变动数值]
        Map<Integer, Integer> goodsIdChange = new HashMap<>();
        // 保存需要删除的清单商品数据ID
        List<Integer> deletedItem = new ArrayList<>();

        List<PurchaseItemEntity> purchaseItemEntities = purchaseItemMapper.selectList(new QueryWrapper<PurchaseItemEntity>().eq("purchase_id", purchaseId));
        List<PurchaseItemEntity> collect = purchaseItemEntities.stream().map(item -> {
            if (purchaseItemModify.containsKey(item.getPurchaseItemId())) {
                PurchaseItemEntity purchaseItemEntity = purchaseItemModify.get(item.getPurchaseItemId());
                goodsIdChange.put(purchaseItemEntity.getGoodsId(), item.getCount() - purchaseItemEntity.getCount());
                item.setCount(purchaseItemEntity.getCount());
            } else {
//                goodsIdChange.put(item.getGoodsId(), item.getCount());
//                deletedItem.add(item.getPurchaseItemId());
            }
            return item;
        }).collect(Collectors.toList());
        List<Integer> goodsIdList = new ArrayList<>();
        goodsIdChange.forEach((key,item)->{
            goodsIdList.add(key);
        });
        // 恢复合同-商品信息
        if(purchaseService.resetContractItemListByContractId(purchaseEntityById.getContractId(), goodsIdChange)<1) {
            throw new Exception("恢复合同-商品信息异常");
        }
        // 恢复商品库存信息
        if(purchaseService.resetGoodsListByGoodsId(goodsIdList,goodsIdChange)<1) {
            throw new Exception("恢复商品库存信息异常");
        }
        // 删除清单中部分数据项
        System.out.println(deletedItem);
//        if(purchaseItemMapper.deleteBatchIds(deletedItem)<1) {
//            throw new Exception("删除清单中部分数据项异常");
//        }
        // 修改清单部分数据项内容
        if(purchaseItemMapper.updateListByPurchaseItemId(purchaseItemEntities)<1) {
            throw new Exception("修改清单部分数据项内容异常");
        }
        return 1;
    }

    public int savePurchaseItemList(Integer purchaseId, List<PurchaseItemEntity> purchaseItemEntityList) {
        Optional.ofNullable(purchaseMapper.selectById(purchaseId)).ifPresent(item->{
            if(item.getPayStatus()==1) {
                throw new RuntimeException("当前清单已支付，不可进行此操作");
            }
//            if(baseMapper.deleteBatchIds(purchaseItemEntityList.stream().map(PurchaseItemEntity::getPurchaseItemId).collect(Collectors.toList()))==0){
//                throw new RuntimeException("删除合同-商品数据异常");
//            }
        });
        List<Integer> GoodsIdCollect = purchaseItemEntityList.stream().map(PurchaseItemEntity::getGoodsId).collect(Collectors.toList());
        List<GoodsEntity> goodsEntities = goodsMapper.selectBatchIds(GoodsIdCollect);
        Map<Integer, GoodsEntity> goodsEntityMap = new HashMap<>();
        List<Object> collect = goodsEntities.stream().map(item -> {
            goodsEntityMap.put(item.getGoodsId(), item);
            return item;
        }).collect(Collectors.toList());
        List<Object> collect1 = purchaseItemEntityList.stream().map(item -> {
            if (goodsEntityMap.containsKey(item.getGoodsId())) {
                GoodsEntity goodsEntity = goodsEntityMap.get(item.getGoodsId());
                item.setPerPrice(goodsEntity.getPerPrice());
//                item.setPrice(goodsEntity.getPerPrice() * item.getCount());
            }
            return item;
        }).collect(Collectors.toList());
        return baseMapper.insertBatch(purchaseItemEntityList);
    }

}