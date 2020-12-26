package com.dgut.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dgut.dto.PageDTO;
import com.dgut.dto.ContractDTO;
import com.dgut.entity.ContractEntity;
import com.dgut.mapper.ContractMapper;
import com.dgut.service.ContractService;
import com.dgut.utils.ResultUtils;
import com.dgut.utils.SnowFlakeUtil;
import com.dgut.vo.PageResult;
import com.dgut.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api(value = "合同控制器", description = "合同控制器")
public class ContractController {

    @Autowired
    ContractMapper contractMapper;

    @Resource
    ContractService contractService;

    @ApiOperation(value = "合同[单一数据]", httpMethod = "GET")
    @GetMapping("/contract/{contractId}")
    public Result<?> getContract(@PathVariable("contractId") Integer contractId) {
        return ResultUtils.success(contractMapper.selectById(contractId));
    }

    @ApiOperation(value = "合同[列表]", httpMethod = "GET")
    @GetMapping("/contract")
    public PageResult getContractList(ContractDTO contractDTO, PageDTO pageDTO) {
        QueryWrapper<ContractEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .like("contract_name", contractDTO.getContractName()!=null?contractDTO.getContractName():"")
                .like("first_party", contractDTO.getFirstParty()!=null?contractDTO.getFirstParty():"");
        if(contractDTO.getSalespersonId() != null)queryWrapper.eq("salesperson_id", contractDTO.getSalespersonId());
        if(contractDTO.getCustomerId() != null)queryWrapper.eq("customer_id", contractDTO.getCustomerId());
        IPage<ContractEntity> contractPage = new Page<>(pageDTO.getPage(), pageDTO.getLimit());
        contractPage = contractMapper.selectPage(contractPage, queryWrapper);
        return ResultUtils.pageResult(contractPage);
    }

    @ApiOperation(value = "合同添加[单一数据]", httpMethod = "POST")
    @PostMapping("/contract")
    public Result<?> saveContract(@RequestBody ContractEntity contractEntity) {
        return ResultUtils.success(contractService.saveContract(contractEntity));
    }

    @ApiOperation(value = "合同信息修改")
    @PutMapping("/contract")
    public Result<?> updateContract(ContractEntity contractEntity) throws Exception {
        ContractEntity contractEntity1 = contractMapper.selectById(contractEntity.getContractId());
        if(contractEntity1 == null) {
            throw new Exception("查无此合同信息");
        }
        contractEntity.setVersion(contractEntity1.getVersion()-1);
        return ResultUtils.success(contractMapper.updateById(contractEntity));
    }

    @ApiOperation(value = "合同信息删除")
    @PostMapping("/contract/{contractId}")
    public Result<?> deleteContract(@PathVariable("contractId") Integer contractId) throws Exception {
        ContractEntity contract = contractMapper.selectById(contractId);
        if(contract.getStatus()>0) {
            throw new Exception("当前合同状态不允许的删除");
        }
        return ResultUtils.success(contractMapper.deleteById(contractId));
    }

}
