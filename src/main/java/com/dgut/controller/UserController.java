package com.dgut.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dgut.dto.PageDTO;
import com.dgut.dto.UserInfoDto;
import com.dgut.entity.*;
import com.dgut.mapper.UserMapper;
import com.dgut.vo.PageResult;
import com.dgut.vo.Result;
import com.dgut.service.Impl.UserServiceImpl;
import com.dgut.utils.ResultUtils;
import com.dgut.vo.UserInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api(value = "用户控制器", description = "用户控制器")
public class UserController extends BaseController{
    @Resource
    UserServiceImpl userServiceImpl;

    @Autowired
    UserMapper userMapper;

    @ApiOperation("获取用户列表")
    @GetMapping("/user")
    @Transactional
    public PageResult getUserList(UserInfoDto userInfoDto, PageDTO pageDTO) {
        String username = userInfoDto.getUsername() != null?userInfoDto.getUsername():"";
        IPage<UserInfoVO> userPage = new Page<>(pageDTO.getPage(), pageDTO.getLimit());
        userPage = userMapper.selectPageVo(userPage, username);
        return ResultUtils.pageResult(userPage);
    }

    @ApiOperation("获取所有用户列表")
    @GetMapping("/user/all")
    @Transactional
    public Result<?> getUserList() {
        return ResultUtils.success(userMapper.selectList(null));
    }

    @GetMapping("/tt")
//    @PreAuthorize("hasRole('admin')")
    public Result<?> tt(Authentication authentication) {
        return ResultUtils.success(authentication);
    }

    @GetMapping("/test")
    public Result<?> test(
            @RequestBody AddressBookEntity addressBook,
            @RequestBody AdminEntity admin,
            @RequestBody ContractEntity contract,
            @RequestBody ContractItemEntity contractItem,
            @RequestBody CustomerEntity customer,
            @RequestBody GoodsEntity goods,
            @RequestBody GoodsInEntity goodsIn,
            @RequestBody LogisticsEntity logistics,
            @RequestBody PurchaseEntity purchase,
            @RequestBody PurchaseItemEntity purchaseItem,
            @RequestBody SalespersonEntity salesperson,
            @RequestBody UserEntity userEntity
    ) {
        System.out.println(addressBook);
        System.out.println(admin);
        System.out.println(contract);
        System.out.println(contractItem);
        System.out.println(customer);
        System.out.println(goods);
        System.out.println(goodsIn);
        System.out.println(logistics);
        System.out.println(purchase);
        System.out.println(purchaseItem);
        System.out.println(salesperson);
        System.out.println(userEntity);
        return ResultUtils.success();
    }

//    @ApiOperation("获取用户基本信息")
//    @RequestMapping("/getUserInfo")
//    public Result<?> getUserInfo(Authentication authentication) {
//        UserInfoDto userInfoVO = new UserInfoDto(this.getUser(authentication.getName()));
//        System.out.println(this.getAuthentication());
//        System.out.println(authentication.getAuthorities());
//        return ResultUtils.success(userInfoVO);
//    }
//    @ApiOperation("更新用户基本信息")
//    @PutMapping("/updateUserInfo")
//    public Result<?> updateUserInfo(UserInfoDto updateUser, Authentication authentication) {
//        userServiceImpl.updateUserInfo(updateUser,authentication.getName());
//        return ResultUtils.success();
//    }
//
//    private UserEntity getUser(String id){
//        return userDao.findById(id).orElseThrow(()-> new RuntimeException("找不到这个用户:"+id));
//    }



}
