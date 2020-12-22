package com.dgut.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dgut.entity.LogisticsEntity;
import com.dgut.mapper.LogisticsMapper;
import com.dgut.service.LogisticsService;
import com.dgut.utils.ResultUtils;
import com.dgut.utils.SnowFlakeUtil;
import com.dgut.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LogisticsServiceImpl implements LogisticsService {

    @Autowired
    LogisticsMapper logisticsMapper;

    public int saveLogistics(LogisticsEntity logisticsEntity) throws Exception {
        logisticsEntity.setLogisticsNo("logistics_" + SnowFlakeUtil.getId());
        return logisticsMapper.insert(logisticsEntity);
    }

    public LogisticsEntity getLogisticsByLogisticsId(Integer logisticsId) {
        return Optional.ofNullable(logisticsMapper.selectById(logisticsId))
                .orElseThrow(()->new RuntimeException("该id:"+logisticsId+"所查找的物流不存在"));
    }

    public LogisticsEntity getLogisticsByLogisticsNo(String logisticsNo) {
        return Optional.ofNullable(logisticsMapper.selectOne(new QueryWrapper<LogisticsEntity>().eq("logistics_no", logisticsNo)))
                .orElseThrow(()->new RuntimeException("该编号:"+logisticsNo+"所查找的物流不存在"));
    }

}
