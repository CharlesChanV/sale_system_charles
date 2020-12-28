package com.dgut.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dgut.dto.StatisticsSearchDTO;
import com.dgut.entity.ContractEntity;
import com.dgut.entity.CustomerEntity;
import com.dgut.entity.PurchaseEntity;
import com.dgut.entity.PurchaseItemEntity;
import com.dgut.mapper.*;
import com.dgut.service.StatisticsService;
import com.dgut.vo.StatisticsBaseVO;
import com.dgut.vo.StatisticsSearchGoodsVO;
import com.dgut.vo.StatisticsSearchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    ContractMapper contractMapper;
    @Autowired
    PurchaseMapper purchaseMapper;
    @Autowired
    PurchaseItemMapper purchaseItemMapper;
    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    SalespersonMapper salespersonMapper;
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    GoodsInMapper goodsInMapper;
    @Autowired
    UserMapper userMapper;


    @Override
    public StatisticsBaseVO getStatisticsBaseInfo() {
        StatisticsBaseVO statisticsBase = new StatisticsBaseVO();
        statisticsBase.setContractCount(contractMapper.selectCount(null));
        statisticsBase.setPurchaseCount(purchaseMapper.selectCount(null));
        statisticsBase.setCustomerCount(customerMapper.selectCount(null));
        statisticsBase.setSalespersonCount(salespersonMapper.selectCount(null));
        statisticsBase.setGoodsCount(goodsMapper.selectCount(null));
        statisticsBase.setGoodsInCount(goodsInMapper.selectCount(null));
        statisticsBase.setUserCount(userMapper.selectCount(null));
        return statisticsBase;
    }
    @Override
    public StatisticsSearchVO searchStatisticsInfo(StatisticsSearchDTO searchDTO) {
        StatisticsSearchVO statisticsInfo = new StatisticsSearchVO();
        Integer customerId = searchDTO.getCustomerId();
        Integer salespersonId = searchDTO.getSalespersonId();
        if (customerId != null&&customerId != 0) {
            statisticsInfo.setContractCount(contractMapper.selectCount(new QueryWrapper<ContractEntity>().eq("customer_id", customerId)));
            statisticsInfo.setPurchaseCount(purchaseMapper.selectCount(new QueryWrapper<PurchaseEntity>().eq("customer_id", customerId)));
            return statisticsInfo;
        }
        if (salespersonId != null&&salespersonId != 0) {
            statisticsInfo.setContractCount(contractMapper.selectCount(new QueryWrapper<ContractEntity>().eq("salesperson_id", salespersonId)));
//            statisticsInfo.setPurchaseCount(purchaseMapper.selectCount(new QueryWrapper<PurchaseEntity>().eq("salesperson_id", salespersonId)));
            return statisticsInfo;
        }
        return null;
    }
    @Override
    public StatisticsSearchGoodsVO searchStatisticsInfoByGoodsId(Integer goodsId) {
        List<PurchaseItemEntity> purchaseItemList = purchaseItemMapper.selectList(new QueryWrapper<PurchaseItemEntity>().eq("goods_id", goodsId));
        if (purchaseItemList.isEmpty()) {
            System.out.println(purchaseItemList);
            return new StatisticsSearchGoodsVO();
        }
        HashMap<Integer, Integer> purchaseCountList = new HashMap<>();
        int totalCount = 0;
        Double totalPrice = 0.0;
        int purchaseItemCount = 0;
        int purchaseCount = 0;
        for(PurchaseItemEntity purchaseItem : purchaseItemList) {
            if(purchaseCountList.containsKey(purchaseItem.getPurchaseId())) {
                int i = purchaseCountList.get(purchaseItem.getPurchaseId())+1;
                purchaseCountList.remove(purchaseItem.getPurchaseId());
                purchaseCountList.put(purchaseItem.getPurchaseId(), i);
            }else{
                purchaseCountList.put(purchaseItem.getPurchaseId(), 1);
            }
            purchaseItemCount = purchaseItemCount + 1;
            totalCount = totalCount + purchaseItem.getCount();
//            System.out.println(purchaseItem.getPerPrice());
//            System.out.println(purchaseItem.getCount());
//            System.out.println(purchaseItem.getPerPrice()*purchaseItem.getCount());
            BigDecimal a = BigDecimal.valueOf(purchaseItem.getPerPrice());
            BigDecimal b = new BigDecimal(purchaseItem.getCount().toString());
            totalPrice = totalPrice + a.multiply(b).doubleValue();
        }
        purchaseCount = purchaseCountList.size();
        StatisticsSearchGoodsVO statisticsSearchGoodsVO = new StatisticsSearchGoodsVO();
        statisticsSearchGoodsVO.setTotalCount(totalCount);
        statisticsSearchGoodsVO.setTotalPrice(totalPrice);
        statisticsSearchGoodsVO.setPurchaseItemCount(purchaseItemCount);
        statisticsSearchGoodsVO.setPurchaseCount(purchaseCount);
        return statisticsSearchGoodsVO;
    }
}
