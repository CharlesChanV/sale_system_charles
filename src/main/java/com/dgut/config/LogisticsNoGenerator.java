package com.dgut.config;

import com.dgut.utils.SnowFlakeUtil;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.UUIDGenerator;

import java.io.Serializable;

public class LogisticsNoGenerator extends UUIDGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        String id =  "Logistics" + SnowFlakeUtil.getId();
        return (Serializable) id;
    }
}
