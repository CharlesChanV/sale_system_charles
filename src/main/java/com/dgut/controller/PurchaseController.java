package com.dgut.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dgut.dto.PurchaseSearchDTO;
import com.dgut.dto.PageDTO;
import com.dgut.entity.ContractEntity;
import com.dgut.entity.ContractItemEntity;
import com.dgut.entity.PurchaseEntity;
import com.dgut.entity.PurchaseItemEntity;
import com.dgut.mapper.ContractMapper;
import com.dgut.mapper.PurchaseItemMapper;
import com.dgut.mapper.PurchaseMapper;
import com.dgut.service.ContractService;
import com.dgut.service.LogisticsService;
import com.dgut.service.PurchaseItemService;
import com.dgut.service.PurchaseService;
import com.dgut.utils.ResultUtils;
import com.dgut.vo.PageResult;
import com.dgut.vo.PurchaseWithItemVO;
import com.dgut.vo.Result;
import com.dgut.vo.UserInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Api(value = "采购清单控制器", description = "采购清单控制器")
public class PurchaseController {
    @Autowired
    PurchaseMapper purchaseMapper;
    @Autowired
    PurchaseItemMapper purchaseItemMapper;
    @Autowired
    PurchaseService purchaseService;
    @Autowired
    PurchaseItemService purchaseItemService;
    @Autowired
    LogisticsService logisticsService;
    @Autowired
    ContractMapper contractMapper;
    @ApiOperation(value = "采购清单[单一数据]", httpMethod = "GET")
    @GetMapping("/purchase/{purchaseId}")
    public Result<?> getPurchase(@PathVariable("purchaseId") Integer purchaseId) {
        return ResultUtils.success(purchaseMapper.selectById(purchaseId));
    }

    @ApiOperation(value = "采购清单[列表]", httpMethod = "GET")
    @GetMapping("/purchase")
    public PageResult getPurchaseList(PurchaseSearchDTO purchaseDTO, PageDTO pageDTO) {
        IPage<PurchaseWithItemVO> purchasePage = new Page<>(pageDTO.getPage(), pageDTO.getLimit());
        purchasePage = purchaseMapper.selectPageVo(purchasePage,purchaseDTO);
        return ResultUtils.pageResult(purchasePage);
    }

    @ApiOperation(value = "采购清单信息修改")
    @PutMapping("/purchase")
    public Result<?> updatePurchase(PurchaseEntity purchaseEntity) throws Exception {
        PurchaseEntity purchaseEntity1 = purchaseMapper.selectById(purchaseEntity.getPurchaseId());
        if(purchaseEntity1 == null) {
            throw new Exception("查无此采购清单信息");
        }
        purchaseEntity.setVersion(purchaseEntity1.getVersion()-1);
        return ResultUtils.success(purchaseMapper.updateById(purchaseEntity));
    }

    @ApiOperation(value = "采购清单信息删除")
    @PostMapping("/purchase/{purchaseId}")
    public Result<?> deletePurchase(@PathVariable("purchaseId") Integer purchaseId) throws Exception {
        return ResultUtils.success(purchaseService.deletePurchaseByPurchaseId(purchaseId));
    }
    // TODO:待测试
    @ApiOperation(value = "采购清单信息新增[商品清单新增]")
    @PostMapping("/purchase/contract/{contractId}")
    public Result<?> savePurchase(@PathVariable("contractId") Integer contractId, @RequestBody String json, Authentication authentication) throws Exception {
        ContractEntity contractEntity = contractMapper.selectById(contractId);
        if(contractEntity == null) {
            throw new Exception("合同不存在");
        }
        if(contractEntity.getStatus() != 1) {
            throw new Exception("该合同所处状态无法进行此操作");
        }
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setContractId(contractId);
        purchaseEntity.setCustomerId(contractEntity.getCustomerId());
        purchaseEntity.setUserId((String) authentication.getPrincipal());
        PurchaseEntity purchaseTarget = purchaseService.savePurchase(purchaseEntity);
        // 接收body中的purchaseItemList
        JSONObject jsonObject = JSONObject.parseObject(json);
        List<PurchaseItemEntity> purchaseItemList = JSON.parseArray(jsonObject.getString("purchaseItemList"), PurchaseItemEntity.class);
        return ResultUtils.success(purchaseItemService.savePurchaseItemList(purchaseTarget.getPurchaseId(), purchaseItemList));
    }
    @ApiOperation(value = "支付采购清单")
    @PostMapping("/purchase/{purchaseId}/pay")
    public Result<?> payPurchase(@PathVariable("purchaseId") Integer purchaseId) throws Exception {
//        PurchaseEntity purchaseEntity = new PurchaseEntity();
//        purchaseEntity.setPurchaseId(purchaseId);
//        purchaseEntity.setPayStatus((byte)1);
        PurchaseEntity purchaseEntity1 = purchaseMapper.selectById(purchaseId);
        if(purchaseEntity1 == null) {
            throw new Exception("清单ID不存在");
        }
        if(purchaseEntity1.getPayStatus() == 1) {
            throw new Exception("该清单已支付请勿重复操作");
        }
        List<PurchaseItemEntity> purchaseItemList = purchaseItemMapper.selectList(new QueryWrapper<PurchaseItemEntity>().eq("purchase_id", purchaseId));
        double total_price = 0.00;
        if(!purchaseItemList.isEmpty()) {
            List<PurchaseItemEntity> purchaseItemListCollect = purchaseItemList.stream().map(item -> {
                item.setPrice(item.getPerPrice() * item.getCount());
                return item;
            }).collect(Collectors.toList());
            total_price = purchaseItemListCollect.stream().mapToDouble(PurchaseItemEntity::getPrice).sum();
            purchaseItemMapper.updateListByPurchaseItemId(purchaseItemListCollect);
        }
        purchaseEntity1.setTotalPrice(total_price);
        purchaseEntity1.setPayStatus((byte)1);
        purchaseEntity1.setVersion(purchaseEntity1.getVersion()-1);
        int update_res = purchaseMapper.updateById(purchaseEntity1);
//        int update_res = purchaseService.updatePurchase(purchaseEntity);
//        if(logisticsService.saveLogistics(logisticsEntity)!=1) {
//            throw new Exception("新增异常");
//        }
        return ResultUtils.success(update_res);
    }
    @ApiOperation(value = "采购清单发货")
    @PostMapping("/purchase/{purchaseId}/deliver")
    public Result<?> deliverPurchase(@PathVariable("purchaseId") Integer purchaseId, Authentication authentication) throws Exception {
        return ResultUtils.success(purchaseService.deliverPurchase(purchaseId, (String) authentication.getPrincipal()));
    }

}
