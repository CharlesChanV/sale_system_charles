package com.dgut.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dgut.dto.UserRegisterDTO;
import com.dgut.entity.RoleEntity;
import com.dgut.entity.UserEntity;
import com.dgut.mapper.UserMapper;
import com.dgut.utils.ResultUtils;
import com.dgut.vo.Result;
import com.dgut.vo.UserRoleInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation(value = "用户注册")
    @PostMapping("/common/register")
    public Result<?> register(UserRegisterDTO userRegisterDTO) {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("username", userRegisterDTO.getUsername());
        UserEntity userEntity = userMapper.selectOne(wrapper);
        if(userEntity != null) {
//            throw new UsernameIsExitedException("用户已经存在");
            return ResultUtils.error(-1,"用户已经存在");
        }
        userRegisterDTO.setPassword(bCryptPasswordEncoder.encode(userRegisterDTO.getPassword()));
        return ResultUtils.success(userMapper.insert(new UserEntity(userRegisterDTO)));
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
        return ResultUtils.success(userWithRole);
    }

}
