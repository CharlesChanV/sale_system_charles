package com.dgut.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dgut.entity.ContractEntity;
import com.dgut.entity.ContractItemEntity;
import com.dgut.entity.PurchaseEntity;
import com.dgut.mapper.ContractItemMapper;
import com.dgut.mapper.ContractMapper;
import com.dgut.mapper.PurchaseMapper;
import com.dgut.service.ContractService;
import com.dgut.utils.SnowFlakeUtil;
import com.dgut.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    ContractMapper contractMapper;
    @Autowired
    ContractItemMapper contractItemMapper;
    @Autowired
    PurchaseMapper purchaseMapper;

    public int saveContract(ContractEntity contractEntity) {
        contractEntity.setContractNo("contract_" + SnowFlakeUtil.getId());
        return contractMapper.insert(contractEntity);
    }

    public int checkFinishContract(Integer contractId) throws Exception {
        ContractEntity contract = contractMapper.selectById(contractId);
        List<ContractItemEntity> contractItemList = contractItemMapper.selectList(new QueryWrapper<ContractItemEntity>().eq("contract_id", contractId));
        if(contractItemList.isEmpty()) {
            throw new Exception("检查是否完成合同异常");
        }
        for(ContractItemEntity contractItem :contractItemList) {
            if(contractItem.getLeaveCount()>0) {
                return 0;
            }
        }
        List<PurchaseEntity> purchaseList = purchaseMapper.selectList(new QueryWrapper<PurchaseEntity>().eq("contract_id", contractId));
        for(PurchaseEntity purchase :purchaseList) {
            if(purchase.getDeliverStatus() == 0) {
                return 0;
            }
        }
        contract.setStatus((byte)2);
        contract.setVersion(contract.getVersion()-1);
        return contractMapper.updateById(contract);
    }
}
