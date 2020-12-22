package com.dgut.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dgut.dto.PageDTO;
import com.dgut.dto.PurchaseItemUpdateListDTO;
import com.dgut.dto.PurchaseSearchDTO;
import com.dgut.entity.PurchaseEntity;
import com.dgut.entity.PurchaseItemEntity;
import com.dgut.mapper.PurchaseItemMapper;
import com.dgut.mapper.PurchaseMapper;
import com.dgut.service.PurchaseItemService;
import com.dgut.service.PurchaseService;
import com.dgut.utils.ResultUtils;
import com.dgut.vo.PageResult;
import com.dgut.vo.PurchaseWithItemVO;
import com.dgut.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Api(value = "采购清单数据项控制器", description = "采购清单数据项控制器")
public class PurchaseItemController {

    @Autowired
    PurchaseItemMapper purchaseItemMapper;

    @Autowired
    PurchaseMapper purchaseMapper;

    @Autowired
    PurchaseService purchaseService;

    @Autowired
    PurchaseItemService purchaseItemService;

    @ApiOperation(value = "批量修改清单数据项", httpMethod = "POST")
    @PostMapping("/purchase/{purchaseId}/modifyItem")
    public Result<?> modifyPurchaseItemList(@PathVariable("purchaseId") Integer purchaseId, @RequestBody String json) throws Exception {
        JSONObject jsonObject = JSONObject.parseObject(json);
        List<PurchaseItemEntity> purchaseItemEntityList = JSON.parseArray(jsonObject.getString("purchaseItemEntityList"), PurchaseItemEntity.class);
        return ResultUtils.success(purchaseItemService.updatePurchaseItemList(purchaseId, purchaseItemEntityList));
    }
    @ApiOperation(value = "批量删除清单数据项", httpMethod = "POST")
    @PostMapping("/purchase/{purchaseId}/deleteItem")
    public Result<?> deletePurchaseItemList(@PathVariable("purchaseId") Integer purchaseId, @RequestBody String json) throws Exception {
        JSONObject jsonObject = JSONObject.parseObject(json);
        List<PurchaseItemEntity> purchaseItemEntityList = JSON.parseArray(jsonObject.getString("purchaseItemEntityList"), PurchaseItemEntity.class);
        return ResultUtils.success(purchaseItemService.deletePurchaseItemList(purchaseId, purchaseItemEntityList));
    }

    @ApiOperation(value = "采购清单数据项[列表]", httpMethod = "GET")
    @GetMapping("/purchase/{purchaseId}/item")
    public PageResult getPurchaseItemList(@PathVariable("purchaseId") Integer purchaseId, PageDTO pageDTO) {
        IPage<PurchaseItemEntity> purchaseItemPage = new Page<>(pageDTO.getPage(), pageDTO.getLimit());
        purchaseItemPage = purchaseMapper.selectItemByPurchaseIdPageVo(purchaseItemPage,purchaseId);
        return ResultUtils.pageResult(purchaseItemPage);
    }

}
