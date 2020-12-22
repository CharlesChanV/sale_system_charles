package com.dgut.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dgut.dto.GoodsInDTO;
import com.dgut.dto.PurchaseSearchDTO;
import com.dgut.entity.GoodsInEntity;
import com.dgut.entity.LogisticsEntity;
import com.dgut.vo.PurchaseWithItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Mapper
@Repository
public interface GoodsInMapper extends BaseMapper<GoodsInEntity> {
    @Transactional
    IPage<GoodsInEntity> selectPageVo(IPage<?> page, GoodsInDTO goodsInDTO);
    LogisticsEntity selectLogisticsByLogisticsId(Integer logistics_id);
}
