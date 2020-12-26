package com.dgut.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dgut.dto.PageDTO;
import com.dgut.entity.ContractEntity;
import com.dgut.entity.ContractItemEntity;
import com.dgut.entity.PurchaseItemEntity;
import com.dgut.mapper.ContractItemMapper;
import com.dgut.mapper.ContractMapper;
import com.dgut.service.ContractItemService;
import com.dgut.utils.ResultUtils;
import com.dgut.vo.ContractItemVO;
import com.dgut.vo.PageResult;
import com.dgut.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(value = "合同-商品控制器", description = "合同-商品控制器")
public class ContractItemController {

    @Autowired
    ContractItemService contractItemService;

    @Autowired
    ContractItemMapper contractItemMapper;

    @Autowired
    ContractMapper contractMapper;

    @ApiOperation(value = "合同-商品数据列表", httpMethod = "GET")
    @GetMapping("/contract/{contractId}/item")
    public PageResult getContractItemList(@PathVariable("contractId") Integer contractId, PageDTO pageDTO) {
        IPage<ContractItemVO> contractItemPage = new Page<>(pageDTO.getPage(), pageDTO.getLimit());
        contractItemPage = contractItemMapper.selectItemByContractIdPageVo(contractItemPage,contractId);
        return ResultUtils.pageResult(contractItemPage);
    }
//TODO:完善功能并新增一个接口（批量增加合同-商品数据项）
    @ApiOperation(value = "批量新增合同-商品数据项", httpMethod = "POST")
    @PostMapping("/contract/{contractId}/addItem")
    public Result<?> addContractItemList(@PathVariable("contractId") Integer contractId, @RequestBody String json) throws Exception {
        JSONObject jsonObject = JSONObject.parseObject(json);
        List<ContractItemEntity> contractItemEntityList = JSON.parseArray(jsonObject.getString("contractItemList"), ContractItemEntity.class);
        return ResultUtils.success(contractItemService.saveContractItemList(contractId, contractItemEntityList));
    }
    @ApiOperation(value = "批量修改合同-商品数据项", httpMethod = "POST")
    @PutMapping("/contract/{contractId}/modifyItem")
    public Result<?> modifyContractItemList(@PathVariable("contractId") Integer contractId, @RequestBody String json) throws Exception {
        JSONObject jsonObject = JSONObject.parseObject(json);
        List<ContractItemEntity> contractItemEntityList = JSON.parseArray(jsonObject.getString("contractItemList"), ContractItemEntity.class);
        return ResultUtils.success(contractItemService.modifyContractItemList(contractId, contractItemEntityList));
    }
    //TODO:待测试
    @ApiOperation(value = "批量删除合同-商品数据项", httpMethod = "POST")
    @PostMapping("/contract/{contractId}/deleteItem")
    public Result<?> deleteContractItemList(@PathVariable("contractId") Integer contractId, @RequestBody String json) throws Exception {
        JSONObject jsonObject = JSONObject.parseObject(json);
        List<Integer> contractItemEntityList = JSON.parseArray(jsonObject.getString("contractItemIds"), Integer.class);
        return ResultUtils.success(contractItemService.deleteContractItemList(contractId, contractItemEntityList));
    }
}
