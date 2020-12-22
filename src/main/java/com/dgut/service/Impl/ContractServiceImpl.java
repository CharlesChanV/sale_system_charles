package com.dgut.service.Impl;

import com.dgut.entity.ContractEntity;
import com.dgut.mapper.ContractMapper;
import com.dgut.service.ContractService;
import com.dgut.utils.SnowFlakeUtil;
import com.dgut.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    ContractMapper contractMapper;

    public int saveContract(ContractEntity contractEntity) {
        contractEntity.setContractNo("contract_" + SnowFlakeUtil.getId());
        return contractMapper.insert(contractEntity);
    }
}
