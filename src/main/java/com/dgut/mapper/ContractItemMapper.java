package com.dgut.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dgut.entity.ContractItemEntity;
import com.dgut.entity.GoodsEntity;
import com.dgut.entity.PurchaseItemEntity;
import com.dgut.vo.ContractItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ContractItemMapper extends CommonMapper<ContractItemEntity> {
    public int updateListByContractItemId(List<ContractItemEntity> contractItemEntityList);
    IPage<ContractItemVO> selectItemByContractIdPageVo(IPage<?> page, Integer contract_id);
}
