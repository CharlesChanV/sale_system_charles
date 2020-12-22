package com.dgut.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dgut.dto.PurchaseSearchDTO;
import com.dgut.entity.PurchaseEntity;
import com.dgut.entity.PurchaseItemEntity;
import com.dgut.vo.PurchaseWithItemVO;
import com.dgut.vo.UserInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Repository
public interface PurchaseMapper extends BaseMapper<PurchaseEntity> {
    @Transactional
    IPage<PurchaseWithItemVO> selectPageVo(IPage<?> page, PurchaseSearchDTO purchaseSearchDTO);
    List<PurchaseItemEntity> selectItemByPurchaseId(Integer purchase_id);
    IPage<PurchaseItemEntity> selectItemByPurchaseIdPageVo(IPage<?> page, Integer purchase_id);
}
