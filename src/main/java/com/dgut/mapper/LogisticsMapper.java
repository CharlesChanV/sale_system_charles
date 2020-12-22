package com.dgut.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.dgut.entity.LogisticsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.sql.Wrapper;
import java.util.List;

@Repository
@Mapper
public interface LogisticsMapper extends BaseMapper<LogisticsEntity> {
    @Select("select * from sale_logistics ${ew.customSqlSegment}")
    List<LogisticsEntity> getAll(@Param(Constants.WRAPPER) Wrapper wrapper);
}
