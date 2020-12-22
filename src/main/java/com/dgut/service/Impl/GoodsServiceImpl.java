package com.dgut.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dgut.entity.GoodsEntity;
import com.dgut.mapper.GoodsMapper;
import com.dgut.service.GoodsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, GoodsEntity> implements GoodsService {
    public List<GoodsEntity> getGoodsListByIds(List<Integer> ids) {
        return baseMapper.selectList(new QueryWrapper<GoodsEntity>().in("goods_id",ids));
    }
    public int updateGoodsList(List<GoodsEntity> updateGoodsList) {
        return baseMapper.updateListByGoodsId(updateGoodsList);
    }
}
