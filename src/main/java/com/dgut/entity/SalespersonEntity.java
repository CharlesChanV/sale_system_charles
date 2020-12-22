package com.dgut.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@ApiModel("销售人员信息表")
// jpa
@Entity(name = "salesperson")
@Table
@Data
@TableName(value = "sale_salesperson")
public class SalespersonEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("销售人员ID")
    @TableId(type = IdType.AUTO)
    private Integer salespersonId;
    @ApiModelProperty("销售人员姓名")
    private String name;
    @ApiModelProperty("销售人员联系方式")
    private String phone;
    @ApiModelProperty("销售人员电子邮箱")
    private String email;
//    @ApiModelProperty("创建时间")
//    @CreationTimestamp
//    private Date createTime;
//    @ApiModelProperty("更新时间")
//    @UpdateTimestamp
//    private Date updateTime;
    @ApiModelProperty("状态1正常0禁用")
    @Column(name="status",columnDefinition="tinyint default 1 comment '状态1正常0禁用'")
    private Byte status;
    @ApiModelProperty("销售人员用户ID")
    private String userId;
}
