package com.dgut.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dgut.dto.GoodsInDTO;
import com.dgut.dto.PageDTO;
import com.dgut.dto.PurchaseSearchDTO;
import com.dgut.entity.GoodsInEntity;
import com.dgut.mapper.GoodsInMapper;
import com.dgut.service.GoodsInService;
import com.dgut.utils.ResultUtils;
import com.dgut.vo.PageResult;
import com.dgut.vo.PurchaseWithItemVO;
import com.dgut.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "进货控制器", description = "进货控制器")
public class GoodsInController {

    @Autowired
    GoodsInMapper goodsInMapper;

    @Autowired
    GoodsInService goodsInService;

    @ApiOperation(value = "进货单[单一数据]", httpMethod = "GET")
    @GetMapping("/goodsIn/{goodsInId}")
    public Result<?> getGoodsIn(@PathVariable("goodsInId") Integer goodsInId) {
        return ResultUtils.success(goodsInMapper.selectById(goodsInId));
    }

    @ApiOperation(value = "进货单[列表]", httpMethod = "GET")
    @GetMapping("/goodsIn")
    public PageResult getGoodsInList(GoodsInDTO goodsInDTO, PageDTO pageDTO) {
        IPage<GoodsInEntity> goodsInPage = new Page<>(pageDTO.getPage(), pageDTO.getLimit());
        goodsInPage = goodsInMapper.selectPageVo(goodsInPage,goodsInDTO);
        return ResultUtils.pageResult(goodsInPage);
    }
    @ApiOperation(value = "新增进货单", httpMethod = "GET")
    @PostMapping("/goodsIn")
    public Result<?> saveGoodsIn(GoodsInEntity goodsInEntity) {
        return ResultUtils.success(goodsInService.saveGoodsIn(goodsInEntity));
    }
    @ApiOperation(value = "更新进货单", httpMethod = "PUT")
    @PutMapping("/goodsIn")
    public Result<?> updateGoodsIn(GoodsInEntity goodsInEntity) throws Exception {
//        GoodsInEntity goodsInEntity1 = goodsInMapper.selectById(goodsInEntity.getGoodsInId());
//        if(goodsInEntity1 == null) {
//            throw new Exception("查无该进货单");
//        }
        return ResultUtils.success(goodsInService.updateGoodsIn(goodsInEntity));
    }
}
