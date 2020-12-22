package com.dgut.service;

import com.dgut.entity.ContractEntity;

public interface ContractService {
    /**
     * 新增合同信息
     * @param contractEntity
     * @return
     */
    public int saveContract(ContractEntity contractEntity);

}
