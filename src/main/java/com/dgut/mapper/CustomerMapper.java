package com.dgut.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dgut.dto.CustomerDTO;
import com.dgut.entity.CustomerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CustomerMapper extends BaseMapper<CustomerEntity> {
}
