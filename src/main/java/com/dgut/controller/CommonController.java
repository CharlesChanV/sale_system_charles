package com.dgut.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dgut.dto.UserRegisterDTO;
import com.dgut.entity.*;
import com.dgut.mapper.*;
import com.dgut.utils.ResultUtils;
import com.dgut.vo.Result;
import com.dgut.vo.UserRoleInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@Api(value = "通过控制器", description = "通过控制器")
public class CommonController extends BaseController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    SalespersonMapper salespersonMapper;
    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    RoleUserMapper roleUserMapper;

    @ApiOperation(value = "用户注册")
    @PostMapping("/common/register")
    public Result<?> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("username", userRegisterDTO.getUsername());
        UserEntity userEntity = userMapper.selectOne(wrapper);
        if(userEntity != null) {
//            throw new UsernameIsExitedException("用户已经存在");
            return ResultUtils.error(-1,"用户已经存在");
        }
        userRegisterDTO.setPassword(bCryptPasswordEncoder.encode(userRegisterDTO.getPassword()));
        int insert = userMapper.insert(new UserEntity(userRegisterDTO));
        if(insert > 0) {
            UserEntity newUser = userMapper.selectOne(new QueryWrapper<UserEntity>().eq("username", userRegisterDTO.getUsername()));
            String userId = newUser.getUserId();
            RoleUserEntity roleUserEntity = new RoleUserEntity();
            roleUserEntity.setUserId(userId);
            roleUserEntity.setRoleId("1");
            roleUserMapper.insert(roleUserEntity);
        }
        return ResultUtils.success(insert);
    }
    @ApiOperation(value = "获取用户信息")
    @RequestMapping("/common/getUserInfo")
    public Result<?> getUserInfo(Authentication authentication) {
        String user_id = authentication.getPrincipal().toString();
        UserRoleInfoVO userWithRole = userMapper.getUserWithRoleByUserId(user_id);
        List<String> roles = new ArrayList<>();
        List<RoleEntity> collect = userWithRole.getRoleList().stream().map(item -> {
            System.out.println(item.getName());
            roles.add(item.getName());
            return item;
        }).collect(Collectors.toList());
        userWithRole.setRoles(roles);
        if(roles.contains("ROLE_sale_person")) {
            userWithRole.setSalespersonInfo(salespersonMapper.selectOne(new QueryWrapper<SalespersonEntity>().eq("user_id", user_id)));
        }
        if(roles.contains("ROLE_customer")) {
            userWithRole.setCustomerInfo(customerMapper.selectOne(new QueryWrapper<CustomerEntity>().eq("user_id", user_id)));
        }
        if(roles.contains("ROLE_store_admin")||roles.contains("ROLE_sale_admin")||roles.contains("ROLE_super_admin")) {
            userWithRole.setAdminInfo(adminMapper.selectOne(new QueryWrapper<AdminEntity>().eq("user_id", user_id)));
        }
        return ResultUtils.success(userWithRole);
    }

}
