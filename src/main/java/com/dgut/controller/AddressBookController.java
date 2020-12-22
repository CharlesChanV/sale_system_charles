package com.dgut.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dgut.dto.AddressBookDTO;
import com.dgut.dto.PageDTO;
import com.dgut.entity.AddressBookEntity;
import com.dgut.entity.LogisticsEntity;
import com.dgut.mapper.AddressBookMapper;
import com.dgut.utils.ResultUtils;
import com.dgut.vo.PageResult;
import com.dgut.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "地址薄控制器", description = "地址薄控制器")
public class AddressBookController {

    @Autowired
    AddressBookMapper addressBookMapper;

    @ApiOperation(value = "获取地址薄信息列表")
    @GetMapping("/addressBook")
    public PageResult getAddressBookList(AddressBookDTO addressBookDTO, PageDTO pageDTO) {
        QueryWrapper<AddressBookEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .like("name",addressBookDTO.getName())
                .like("phone",addressBookDTO.getPhone())
                .like("city", addressBookDTO.getCity())
                .like("addressDetail",addressBookDTO.getAddressDetail())
                .like("userId",addressBookDTO.getUserId());
        IPage<AddressBookEntity> addressBookPage = new Page<>(pageDTO.getPage(), pageDTO.getLimit());
        addressBookPage = addressBookMapper.selectPage(addressBookPage, queryWrapper);
        return ResultUtils.pageResult(addressBookPage);
    }
    @ApiOperation(value = "获取地址薄信息[ID]")
    @GetMapping("/addressBook/{addressId}")
    public Result<?> getAddressBookList(@PathVariable("addressId") Integer addressId) {
        return ResultUtils.success(addressBookMapper.selectById(addressId));
    }
    @ApiOperation(value = "地址薄信息修改")
    @PutMapping("/addressBook")
    public Result<?> updateAddressBook(AddressBookEntity addressBookEntity) throws Exception {
        AddressBookEntity addressBookEntity1 = addressBookMapper.selectById(addressBookEntity.getAddressId());
        if(addressBookEntity1 == null) {
            throw new Exception("查无此地址薄信息");
        }
        addressBookEntity.setVersion(addressBookEntity1.getVersion()-1);
        return ResultUtils.success(addressBookMapper.updateById(addressBookEntity));
    }
    @ApiOperation(value = "地址薄信息删除")
    @PostMapping("/addressBook/{addressId}")
    public Result<?> deleteAddressBook(@PathVariable("addressId") Integer addressId) {
        return ResultUtils.success(addressBookMapper.deleteById(addressId));
    }
}
