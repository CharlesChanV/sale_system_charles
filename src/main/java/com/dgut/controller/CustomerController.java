package com.dgut.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dgut.dto.PageDTO;
import com.dgut.dto.SalespersonDTO;
import com.dgut.entity.CustomerEntity;
import com.dgut.entity.SalespersonEntity;
import com.dgut.mapper.CustomerMapper;
import com.dgut.utils.ResultUtils;
import com.dgut.vo.PageResult;
import com.dgut.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "客户控制器", description = "客户控制器")
public class CustomerController {

    @Autowired
    CustomerMapper customerMapper;

    @ApiOperation(value = "客户[单一数据]", httpMethod = "GET")
    @GetMapping("/customer/{customerId}")
    public Result<?> getCustomer(@PathVariable("customerId") Integer customerId) {
        return ResultUtils.success(customerMapper.selectById(customerId));
    }

    @ApiOperation(value = "客户[列表]", httpMethod = "GET")
    @GetMapping("/customer")
    public PageResult getCustomerList(SalespersonDTO salespersonDTO, PageDTO pageDTO) {
        QueryWrapper<CustomerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .like("name", salespersonDTO.getName()!=null?salespersonDTO.getName():"")
                .like("user_id", salespersonDTO.getUserId()!=null?salespersonDTO.getUserId():"")
                .like("phone", salespersonDTO.getPhone()!=null?salespersonDTO.getPhone():"");
        IPage<CustomerEntity> customerPage = new Page<>(pageDTO.getPage(), pageDTO.getLimit());
        customerPage = customerMapper.selectPage(customerPage, queryWrapper);
        return ResultUtils.pageResult(customerPage);
    }

    @ApiOperation(value = "客户信息新增")
    @PostMapping("/customer")
    public Result<?> saveCustomer(@RequestBody CustomerEntity customerEntity) throws Exception {
        CustomerEntity hasUserId = customerMapper.selectOne(new QueryWrapper<CustomerEntity>().eq("user_id", customerEntity.getUserId()));
        if(hasUserId != null) {
            throw new Exception("该用户已存在客户信息");
        }
        return ResultUtils.success(customerMapper.insert(customerEntity));
    }

    @ApiOperation(value = "客户信息修改")
    @PutMapping("/customer")
    public Result<?> updateCustomer(@RequestBody CustomerEntity customerEntity) throws Exception {
        CustomerEntity customerEntity1 = customerMapper.selectById(customerEntity.getCustomerId());
        if(customerEntity1 == null) {
            throw new Exception("查无此客户信息");
        }
        customerEntity.setVersion(customerEntity1.getVersion()-1);
        return ResultUtils.success(customerMapper.updateById(customerEntity));
    }

    @ApiOperation(value = "客户信息删除")
    @PostMapping("/customer/{customerId}")
    public Result<?> deleteCustomer(@PathVariable("customerId") Integer customerId) {
        return ResultUtils.success(customerMapper.deleteById(customerId));
    }

}
