package com.dgut.service;

import com.dgut.entity.GoodsInEntity;

public interface GoodsInService {
    // 创建进货单
    public int saveGoodsIn(GoodsInEntity goodsInEntity);
    // 更新进货单（信息、状态）
    public int updateGoodsIn(GoodsInEntity goodsInEntity) throws Exception;

}
