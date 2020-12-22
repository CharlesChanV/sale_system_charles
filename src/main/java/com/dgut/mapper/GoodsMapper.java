package com.dgut.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dgut.entity.GoodsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GoodsMapper extends CommonMapper<GoodsEntity> {

    public int updateListByGoodsId(List<GoodsEntity> goodsEntityList);

}
