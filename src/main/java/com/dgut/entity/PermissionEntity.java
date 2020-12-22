package com.dgut.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;

@ApiModel("权限信息表")
// jpa
@Entity(name = "permission")
@Table
@Data
@TableName(value = "sale_permission")
public class PermissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("权限ID")
    @TableId(type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("权限名称")
    private String name;
    @ApiModelProperty("权限描述")
    private String description;
    @ApiModelProperty("权限URl")
    private String url;
    @ApiModelProperty("父权限ID")
    private BigInteger pid;
}
