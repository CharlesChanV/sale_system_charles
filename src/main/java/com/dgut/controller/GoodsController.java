package com.dgut.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dgut.dto.PageDTO;
import com.dgut.entity.ContractEntity;
import com.dgut.entity.ContractItemEntity;
import com.dgut.entity.GoodsEntity;
import com.dgut.mapper.GoodsMapper;
import com.dgut.service.GoodsService;
import com.dgut.utils.ResultUtils;
import com.dgut.vo.PageResult;
import com.dgut.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class GoodsController {

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    GoodsService goodsService;

    @ApiOperation(value = "商品数据[ID]", httpMethod = "GET")
    @GetMapping("/goods/{goodsId}")
    public Result<?> getGoods(@PathVariable("goodsId") Integer goodsId) {
        return ResultUtils.success(goodsMapper.selectById(goodsId));
    }
    @ApiOperation(value = "商品数据列表[ID]", httpMethod = "GET")
    @GetMapping("/goods")
    public PageResult getGoodsList(GoodsEntity goodsEntity, PageDTO pageDTO) {
        IPage<GoodsEntity> goodsPage = new Page<>(pageDTO.getPage(), pageDTO.getLimit());
        goodsPage = goodsMapper.selectPage(goodsPage,new QueryWrapper<GoodsEntity>().like("name", goodsEntity.getName()!=null?goodsEntity.getName():""));
        return ResultUtils.pageResult(goodsPage);
    }

    @ApiOperation(value = "商品添加[单一数据]", httpMethod = "POST")
    @PostMapping("/goods")
    public Result<?> saveGoods(GoodsEntity goodsEntity) {
        return ResultUtils.success(goodsMapper.insert(goodsEntity));
    }

    @ApiOperation(value = "商品信息修改")
    @PutMapping("/goods")
    public Result<?> updateGoods(GoodsEntity goodsEntity) throws Exception {
        System.out.println(goodsEntity);
        GoodsEntity goodsEntity1 = goodsMapper.selectById(goodsEntity.getGoodsId());
        if(goodsEntity1 == null) {
            throw new Exception("查无此商品信息");
        }
        goodsEntity.setVersion(goodsEntity1.getVersion()-1);
        return ResultUtils.success(goodsMapper.updateById(goodsEntity));
    }

    @ApiOperation(value = "商品信息删除")
    @PostMapping("/goods/{goodsId}")
    public Result<?> deleteGoods(@PathVariable("goodsId") Integer goodsId) {
        return ResultUtils.success(goodsMapper.deleteById(goodsId));
    }



}
