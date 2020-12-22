package com.dgut.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@ApiModel("权限-角色信息表")
// jpa
@Entity(name = "permission_role")
@Table
@Data
@TableName(value = "sale_permission_role")
public class PermissionRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("ID")
    @TableId(type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("权限ID")
    private String permissionId;
    @ApiModelProperty("角色ID")
    private String roleId;
}
