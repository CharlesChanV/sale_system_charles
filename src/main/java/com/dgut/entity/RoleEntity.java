package com.dgut.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@ApiModel("角色信息表")
// jpa
@Entity(name = "role")
@Table
@Data
@TableName(value = "sale_role")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("角色ID")
    @TableId(type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("角色名称")
    private String name;
    @ApiModelProperty("角色描述")
    private String description;
}
