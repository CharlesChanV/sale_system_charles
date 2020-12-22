package com.dgut.service.Impl;

import com.dgut.entity.GoodsEntity;
import com.dgut.entity.GoodsInEntity;
import com.dgut.mapper.GoodsInMapper;
import com.dgut.mapper.GoodsMapper;
import com.dgut.service.GoodsInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsInServiceImpl implements GoodsInService {

    @Autowired
    GoodsInMapper goodsInMapper;

    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public int saveGoodsIn(GoodsInEntity goodsInEntity) {
        return goodsInMapper.insert(goodsInEntity);
    }

    @Override
    public int updateGoodsIn(GoodsInEntity goodsInEntity) throws Exception {
        GoodsInEntity originGoodsIn = goodsInMapper.selectById(goodsInEntity.getGoodsInId());
        if(originGoodsIn == null) {
            throw new Exception("查无该进货单");
        }
        if(originGoodsIn.getStatus() == 0&&goodsInEntity.getStatus() == 1) {
            // 增加商品库存
            GoodsEntity goodsEntity = goodsMapper.selectById(originGoodsIn.getGoodsId());
            if (goodsEntity==null) {
                throw new Exception("商品信息不存在");
            }
            goodsEntity.setStock(goodsEntity.getStock()+originGoodsIn.getCount());
            goodsEntity.setVersion(goodsEntity.getVersion()-1);
            int i = goodsMapper.updateById(goodsEntity);
            if(i == 0){
                throw new Exception("库存信息变动异常");
            }
        }
        goodsInEntity.setVersion(originGoodsIn.getVersion()-1);
        return goodsInMapper.updateById(goodsInEntity);
    }
}
