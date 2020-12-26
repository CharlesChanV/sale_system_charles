package com.dgut.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dgut.dto.PageDTO;
import com.dgut.dto.SalespersonDTO;
import com.dgut.entity.CustomerEntity;
import com.dgut.entity.LogisticsEntity;
import com.dgut.entity.SalespersonEntity;
import com.dgut.mapper.SalespersonMapper;
import com.dgut.utils.ResultUtils;
import com.dgut.vo.PageResult;
import com.dgut.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "销售人员控制器", description = "销售人员控制器")
public class SalespersonController {

    @Autowired
    SalespersonMapper salespersonMapper;

    @ApiOperation(value = "销售人员[单一数据]", httpMethod = "GET")
    @GetMapping("/salesperson/{salespersonId}")
    public Result<?> getSalesperson(@PathVariable("salespersonId") Integer salespersonId) {
        return ResultUtils.success(salespersonMapper.selectById(salespersonId));
    }
    @ApiOperation(value = "销售人员[列表]", httpMethod = "GET")
    @GetMapping("/salesperson")
    public PageResult getSalespersonList(SalespersonDTO salespersonDTO, PageDTO pageDTO) {
        QueryWrapper<SalespersonEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .like("name", salespersonDTO.getName()!=null?salespersonDTO.getName():"")
                .like("email", salespersonDTO.getEmail()!=null?salespersonDTO.getEmail():"")
                .like("phone", salespersonDTO.getPhone()!=null?salespersonDTO.getPhone():"")
                .like("user_id", salespersonDTO.getUserId()!=null?salespersonDTO.getUserId():"");
        IPage<SalespersonEntity> salespersonPage = new Page<>(pageDTO.getPage(), pageDTO.getLimit());
        salespersonPage = salespersonMapper.selectPage(salespersonPage, queryWrapper);
        return ResultUtils.pageResult(salespersonPage);
    }

    @ApiOperation(value = "销售人员信息新增")
    @PostMapping("/salesperson")
    public Result<?> saveCustomer(@RequestBody SalespersonEntity salespersonEntity) throws Exception {
        SalespersonEntity hasUserId = salespersonMapper.selectOne(new QueryWrapper<SalespersonEntity>().eq("user_id", salespersonEntity.getUserId()));
        if(hasUserId != null) {
            throw new Exception("该用户已存在销售人员信息");
        }
        return ResultUtils.success(salespersonMapper.insert(salespersonEntity));
    }

    @ApiOperation(value = "销售人员信息修改")
    @PutMapping("/salesperson")
    public Result<?> updateSalesperson(@RequestBody SalespersonEntity salespersonEntity) throws Exception {
        SalespersonEntity salespersonEntity1 = salespersonMapper.selectById(salespersonEntity.getSalespersonId());
        if(salespersonEntity1 == null) {
            throw new Exception("查无此销售人员信息");
        }
        salespersonEntity.setVersion(salespersonEntity1.getVersion()-1);
        return ResultUtils.success(salespersonMapper.updateById(salespersonEntity));
    }

    @ApiOperation(value = "销售人员信息删除")
    @PostMapping("/salesperson/{salespersonId}")
    public Result<?> deleteSalesperson(@PathVariable("salespersonId") Integer salespersonId) {
        return ResultUtils.success(salespersonMapper.deleteById(salespersonId));
    }

}
