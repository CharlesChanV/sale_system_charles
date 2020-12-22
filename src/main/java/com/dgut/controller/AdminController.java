package com.dgut.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dgut.dto.AdminDto;
import com.dgut.entity.AdminEntity;
import com.dgut.entity.RoleEntity;
import com.dgut.entity.RoleUserEntity;
import com.dgut.exception.ResultExceptionHandler;
import com.dgut.exception.TokenException;
import com.dgut.mapper.AdminMapper;
import com.dgut.mapper.RoleMapper;
import com.dgut.mapper.RoleUserMapper;
import com.dgut.utils.ResultUtils;
import com.dgut.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "管理员控制器", description = "管理员控制器")
public class AdminController extends BaseController {
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    RoleUserMapper roleUserMapper;
    @Autowired
    AdminMapper adminMapper;

    @ApiOperation(value = "获取角色信息")
    @GetMapping("/admin/getRoleList")
    public Result<?> getRoleList() {
        return ResultUtils.success(roleMapper.selectList(null));
    }

    @ApiOperation(value = "设置用户角色")
    @PostMapping("/admin/setUserRole")
    public Result<?> setUserRole(RoleUserEntity roleUserEntity) throws Exception {
//        System.out.println(roleUserEntity);
        QueryWrapper<RoleUserEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",roleUserEntity.getRoleId());
        wrapper.eq("user_id",roleUserEntity.getUserId());
        List<Map<String, Object>> maps = roleUserMapper.selectMaps(wrapper);
        System.out.println(maps);
        if(!maps.isEmpty()) {
//            return ResultUtils.error(20001,"该用户已存在该角色");
            throw new Exception("该用户已存在该角色");
        }
        return ResultUtils.success(roleUserMapper.insert(roleUserEntity));
    }
    @ApiOperation(value = "删除用户角色")
    @PostMapping("/admin/deleteUserRole")
    public Result<?> deleteUserRole(RoleUserEntity roleUserEntity) throws Exception {
//        System.out.println(roleUserEntity);
        QueryWrapper<RoleUserEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",roleUserEntity.getRoleId());
        wrapper.eq("user_id",roleUserEntity.getUserId());
        int delete = roleUserMapper.delete(wrapper);
        if(delete > 0) {
//            return ResultUtils.error(20001,"该用户已存在该角色");
            return ResultUtils.success();
        }
        throw new Exception("操作失败");
    }

    @ApiOperation(value = "保存用户管理员信息[新增/更新]")
    @PostMapping("/admin/saveAdminInfo")
    public Result<?> saveAdminInfo(AdminDto adminDto) throws Exception {
//        System.out.println(roleUserEntity);
        QueryWrapper<AdminEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",adminDto.getUserId());
        AdminEntity adminEntity = adminMapper.selectOne(queryWrapper);
        if(adminEntity == null) {
            return ResultUtils.success(adminMapper.insert(new AdminEntity(adminDto)));
        }
        AdminEntity newAdmin = new AdminEntity(adminDto);
        newAdmin.setAdminId(adminEntity.getAdminId());
        newAdmin.setVersion(adminEntity.getVersion()-1);
        return ResultUtils.success(adminMapper.updateById(newAdmin));
    }




}
