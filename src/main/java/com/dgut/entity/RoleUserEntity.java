package com.dgut.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@ApiModel("用户-角色信息表")
// jpa
@Entity(name = "role_user")
@Table
@Data
@TableName(value = "sale_role_user")
public class RoleUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("ID")
    @TableId(type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("用户ID")
    private String userId;
    @ApiModelProperty("角色ID")
    private String roleId;
}
