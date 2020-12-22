package com.dgut.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dgut.entity.GoodsEntity;
import com.dgut.entity.PurchaseEntity;

import java.util.List;

public interface GoodsService  extends IService<GoodsEntity> {

    /**
     * 根据ID列表查询商品列表
     * @param ids
     * @return
     */
    public List<GoodsEntity> getGoodsListByIds(List<Integer> ids);

    /**
     * 更新商品列表
     * @param updateGoodsList
     * @return
     */
    public int updateGoodsList(List<GoodsEntity> updateGoodsList);
}
