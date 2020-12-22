package com.dgut.mapper;

import com.dgut.entity.ContractItemEntity;
import com.dgut.entity.GoodsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ContractItemMapper extends CommonMapper<ContractItemEntity> {
    public int updateListByContractItemId(List<ContractItemEntity> contractItemEntityList);
}
