package com.dgut.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dgut.entity.ContractItemEntity;
import com.dgut.entity.GoodsEntity;
import com.dgut.entity.PurchaseItemEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PurchaseItemMapper extends CommonMapper<PurchaseItemEntity> {

    public int updateListByPurchaseItemId(List<PurchaseItemEntity> purchaseItemEntityList);

}
