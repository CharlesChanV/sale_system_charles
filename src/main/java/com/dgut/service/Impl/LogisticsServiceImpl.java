package com.dgut.service.Impl;

import com.dgut.entity.LogisticsEntity;
import com.dgut.mapper.LogisticsMapper;
import com.dgut.service.LogisticsService;
import com.dgut.utils.ResultUtils;
import com.dgut.utils.SnowFlakeUtil;
import com.dgut.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogisticsServiceImpl implements LogisticsService {

    @Autowired
    LogisticsMapper logisticsMapper;

    public int saveLogistics(LogisticsEntity logisticsEntity) throws Exception {
        logisticsEntity.setLogisticsNo("logistics_" + SnowFlakeUtil.getId());
        return logisticsMapper.insert(logisticsEntity);
    }

}
