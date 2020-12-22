package com.dgut.service;

import com.dgut.entity.LogisticsEntity;

public interface LogisticsService {

    public int saveLogistics(LogisticsEntity logisticsEntity) throws Exception;

    public LogisticsEntity getLogisticsByLogisticsId(Integer logisticsId);

    public LogisticsEntity getLogisticsByLogisticsNo(String logisticsNo);

}
