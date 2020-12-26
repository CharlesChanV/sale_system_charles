package com.dgut.vo;

import com.dgut.entity.AdminEntity;
import com.dgut.entity.CustomerEntity;
import com.dgut.entity.RoleEntity;
import com.dgut.entity.SalespersonEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
/**
 * 获取用户登录信息（包含角色信息）
 */
public class UserRoleInfoVO {
    private String userId;
    private String username;
    private String avatar;
    private List<RoleEntity> roleList;
    private List<String> roles;
    private SalespersonEntity salespersonInfo;
    private CustomerEntity customerInfo;
    private AdminEntity adminInfo;
}
