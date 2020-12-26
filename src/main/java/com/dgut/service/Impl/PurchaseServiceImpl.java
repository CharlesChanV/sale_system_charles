package com.dgut.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dgut.dto.GoodsUpdateStockDTO;
import com.dgut.entity.*;
import com.dgut.mapper.*;
import com.dgut.service.*;
import com.dgut.utils.SnowFlakeUtil;
import com.dgut.vo.PurchaseWithItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PurchaseServiceImpl extends ServiceImpl<PurchaseMapper, PurchaseEntity> implements PurchaseService {

    @Autowired
    PurchaseMapper purchaseMapper;
    @Autowired
    PurchaseItemMapper purchaseItemMapper;
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    GoodsService goodsService;
    @Autowired
    ContractService contractService;
    @Autowired
    ContractItemService contractItemService;
    @Autowired
    GoodsInService goodsInService;
    @Autowired
    AddressBookMapper addressBookMapper;
    @Autowired
    LogisticsService logisticsService;
    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    AdminMapper adminMapper;

    @Override
    public PurchaseEntity savePurchase(PurchaseEntity purchaseEntity) throws Exception {
        String no = "purchase_" + SnowFlakeUtil.getId();
        purchaseEntity.setPurchaseNo(no);
        if(purchaseMapper.insert(purchaseEntity) != 1) {
            throw new Exception("采购清单新增异常");
        }
        return purchaseMapper.selectOne(new QueryWrapper<PurchaseEntity>().eq("purchase_no", no));
    }

    @Override
    public int deletePurchaseByPurchaseId(Integer purchaseId) throws Exception {
        PurchaseEntity purchaseEntity = this.getPurchaseEntityById(purchaseId);
//        Optional.ofNullable(purchaseEntity).ifPresentOrElse(purchaseEntity1 -> {
//
//        },()->{
//
//        });
//        if(purchaseEntity == null) {
//            throw new Exception("查无此采购清单信息");
//        }
        if(purchaseEntity.getDeliverStatus() == 1) {
            throw new Exception("该清单已发货无法删除");
        }
        if(purchaseEntity.getPayStatus() == 1) {
            throw new Exception("该清单已支付无法删除");
        }

        QueryWrapper<PurchaseItemEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("purchase_id", purchaseId).select("goods_id", "count");
        List<PurchaseItemEntity> purchaseItemEntities = purchaseItemMapper.selectList(queryWrapper);
        if(purchaseItemEntities.isEmpty()) {
            if(purchaseMapper.deleteById(purchaseEntity.getPurchaseId())>0) {
                return 1;
            }
            throw new Exception("删除清单数据异常异常");
        }
        Map<Integer, Integer> purchaseItemList = new HashMap<>();
        // 将根据purchase_id查询出来的PurchaseItemEntity转化为goods_id->count的键值对purchaseItemList
        List<Integer> goodIdsList = purchaseItemEntities.stream().map(item -> {
            purchaseItemList.put(item.getGoodsId(), item.getCount());
            return item.getGoodsId();
        }).collect(Collectors.toList());
        // 恢复合同-商品信息
        if(this.resetContractItemListByContractId(purchaseEntity.getContractId(),purchaseItemList)!=1){
            throw new Exception("恢复合同-商品信息异常");
        }
//        List<GoodsEntity> goods_id = baseMapper.selectList(Wrappers.<GoodsEntity>lambdaQuery().in("goods_id", goodIdsList));
        if(this.resetGoodsListByGoodsId(goodIdsList, purchaseItemList)!=1) {
            throw new Exception("恢复商品列表货存异常");
        }
//        List<GoodsUpdateStockDTO> collect = purchaseItemEntities.stream().map(GoodsUpdateStockDTO::new).collect(Collectors.toList());
//        Map<String, Object> map = new HashMap<>();
//        map.put("purchase_id",purchaseId);
        if(purchaseItemMapper.delete(new QueryWrapper<PurchaseItemEntity>().eq("purchase_id",purchaseId))==0) {
            throw new Exception("删除清单-商品表数据异常异常");
        }
        if(purchaseMapper.deleteById(purchaseEntity.getPurchaseId())>0) {
            return 1;
        }
        throw new Exception("删除清单数据异常异常");
    }
    // 获取清单信息
    public PurchaseEntity getPurchaseEntityById(Integer id){
        return Optional.ofNullable(purchaseMapper.selectById(id))
                .orElseThrow(()->new RuntimeException("该id:"+id+"所查找的购物清单不存在"));
    }
    // 恢复商品库存信息
    public int resetGoodsListByGoodsId(List<Integer> goodIdsList, Map<Integer, Integer> purchaseItemList) throws Exception {
        // 通过godds_id的list获取List<GoodsEntity>
        List<GoodsEntity> goodsListByIds = goodsService.getGoodsListByIds(goodIdsList);
        // 缺货商品ID列表
        List<Integer> lackGoodsIds = new ArrayList<>();
        // 入货商品列表
//        List<GoodsInEntity> goodsInList = new ArrayList<>();
        // 修改库存后的商品列表
        List<GoodsEntity> updateGoodsList = goodsListByIds.stream().peek(item -> {
            int up_stock = (int) purchaseItemList.get(item.getGoodsId());
            item.setStock(item.getStock() + up_stock);
            if(item.getStock() + up_stock<0) {
                lackGoodsIds.add(item.getGoodsId());
                GoodsInEntity goodsIn = new GoodsInEntity();
                goodsIn.setGoodsId(item.getGoodsId());
                goodsIn.setStatus((byte) 0);
                goodsIn.setCount(-up_stock);
                goodsIn.setRemark("自动入货");
                goodsInService.saveGoodsIn(goodsIn);
//                throw new RuntimeException("存在商品货存不足，商品ID:"+item.getGoodsId());
            }
//            if (up_stock > 0) {
//                item.setStock(item.getStock() + purchaseItemList.get(item.getGoodsId()));
//            }
        }).collect(Collectors.toList());
        if(!lackGoodsIds.isEmpty()){
            throw new Exception("存在商品货存不足，商品ID:"+lackGoodsIds.toString());
        }
        return goodsService.updateGoodsList(updateGoodsList);
    }
    // 恢复合同-商品信息
    public int resetContractItemListByContractId(Integer contractId, Map<Integer, Integer> purchaseItemList) throws Exception {
        System.out.println(contractId);
        System.out.println(purchaseItemList);
        List<ContractItemEntity> contractItemListByContractId = contractItemService.getContractItemListByContractId(contractId);
        if(contractItemListByContractId.isEmpty()) {
            throw new Exception("不存在该合同-商品信息");
        }
        List<ContractItemEntity> updateContractItemList = new ArrayList<>();
        List<ContractItemEntity> collect = contractItemListByContractId.stream().map(item -> {
            if (purchaseItemList.containsKey(item.getGoodsId())) {
                int add_count = purchaseItemList.get(item.getGoodsId());
                // 检测对应合同货品剩余用量是否异常
                if(item.getLeaveCount() + add_count<0) {
                    throw new RuntimeException(item.getContractItemId()+"该合同-商品信息存在剩余不足"+item.getLeaveCount()+" "+add_count);
                }
                item.setLeaveCount(item.getLeaveCount() + add_count);
                updateContractItemList.add(item);
            }
            return item;
        }).collect(Collectors.toList());
        System.out.println(collect);
        if (updateContractItemList.isEmpty()) {
            throw new Exception("无法恢复合同-商品信息");
        }
        return contractItemService.updateListByContractItemId(updateContractItemList);
    }
    // 获取采购清单携带数据项[单一数据][商品数据]
    public PurchaseWithItemVO getPurchaseWithItemByPurchaseId(Integer purchaseId) {
        return new PurchaseWithItemVO();
    }
    // 更新采购清单信息
    public int updatePurchase(PurchaseEntity purchaseEntity) throws Exception {
        PurchaseEntity purchaseEntity1 = purchaseMapper.selectById(purchaseEntity.getPurchaseId());
        if(purchaseEntity1 == null) {
            throw new Exception("采购清单不存在");
        }
        purchaseEntity.setVersion(purchaseEntity1.getVersion()-1);
        int res = purchaseMapper.updateById(purchaseEntity);
        if(res>0&&purchaseEntity.getDeliverStatus() == 1) {
            contractService.checkFinishContract(purchaseEntity1.getContractId());
        }
        return res;
    }
    // 采购清单发货
    public int deliverPurchase(Integer purchaseId, String senderUserId) throws Exception {
        PurchaseEntity purchase = this.getPurchaseEntityById(purchaseId);
        if(purchase.getPayStatus()!=1) {
            throw new Exception("该清单未付款");
        }
        if(purchase.getDeliverStatus()==1) {
            throw new Exception("该清单已经发货，请勿重复操作");
        }
        String addresseeUserId = purchase.getUserId();
        LogisticsEntity logisticsEntity = this.handleLogisticsInfoNew(addresseeUserId, senderUserId);
        int logistics_res = logisticsService.saveLogistics(logisticsEntity);
        if(logistics_res != 1) {
            throw new Exception("物流信息新增异常");
        }
        LogisticsEntity logisticsNew = logisticsService.getLogisticsByLogisticsNo(logisticsEntity.getLogisticsNo());
        purchase.setLogisticsId(logisticsNew.getLogisticsId());
        purchase.setDeliverStatus(1);
        return this.updatePurchase(purchase);
    }

    /**
     * 处理封装物流信息
     * @param addresseeUserId
     * @param senderUserId
     * @return
     * @throws Exception
     */
    public LogisticsEntity handleLogisticsInfoNew(String addresseeUserId, String senderUserId) throws Exception {
        CustomerEntity addressee = customerMapper.selectOne(new QueryWrapper<CustomerEntity>().eq("user_id", addresseeUserId));
        if(addressee == null) {
            throw new Exception("收件人地址尚未完善");
        }
        AdminEntity sender = adminMapper.selectOne(new QueryWrapper<AdminEntity>().eq("user_id", senderUserId));
        if(sender == null) {
            throw new Exception("寄件人地址尚未完善");
        }
        LogisticsEntity logisticsEntity = new LogisticsEntity();
        logisticsEntity.setLogisticsNo("logistics_" + SnowFlakeUtil.getId());
        logisticsEntity.setAddresseeName(addressee.getName());
        logisticsEntity.setAddresseeAddress(addressee.getAddress());
        logisticsEntity.setAddresseePhone(addressee.getPhone());
        logisticsEntity.setPayMode(1);
        logisticsEntity.setCompanyName("顺丰");
        logisticsEntity.setFee(18.00);
        logisticsEntity.setSenderName(sender.getName());
        logisticsEntity.setSenderAddress(sender.getAddress());
        logisticsEntity.setSenderPhone(sender.getPhone());
        return logisticsEntity;
    }
    public LogisticsEntity handleLogisticsInfo(String addresseeUserId, String senderUserId) throws Exception {
        AddressBookEntity addresseeAddress = addressBookMapper.selectOne(new QueryWrapper<AddressBookEntity>().eq("user_id", addresseeUserId));
        if(addresseeAddress == null) {
            throw new Exception("收件人地址尚未完善");
        }
        AddressBookEntity senderAddress = addressBookMapper.selectOne(new QueryWrapper<AddressBookEntity>().eq("user_id", senderUserId));
        if(senderAddress == null) {
            throw new Exception("寄件人地址尚未完善");
        }
        LogisticsEntity logisticsEntity = new LogisticsEntity();
        logisticsEntity.setLogisticsNo("logistics_" + SnowFlakeUtil.getId());
        logisticsEntity.setAddresseeName(addresseeAddress.getName());
        logisticsEntity.setAddresseeAddress(addresseeAddress.getCity()+addresseeAddress.getAddressDetail());
        logisticsEntity.setAddresseePhone(addresseeAddress.getPhone());
        logisticsEntity.setPayMode(1);
        logisticsEntity.setCompanyName("顺丰");
        logisticsEntity.setFee(18.00);
        logisticsEntity.setSenderName(senderAddress.getName());
        logisticsEntity.setSenderAddress(senderAddress.getCity()+senderAddress.getAddressDetail());
        logisticsEntity.setSenderPhone(senderAddress.getPhone());
        return logisticsEntity;
    }
}
