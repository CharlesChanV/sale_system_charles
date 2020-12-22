package com.dgut.vo;

import com.dgut.entity.RoleEntity;
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
//    private String password;
    private List<RoleEntity> roleList;
}
