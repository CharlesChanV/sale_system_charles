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
@ApiModel("客户表")
// jpa
@Entity(name = "customer")
@Table
@Data
@TableName(value = "sale_customer")
public class  CustomerEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("客户ID")
    @TableId(type = IdType.AUTO)
    private Integer customerId;
    @ApiModelProperty("客户名称")
    private String name;
    @ApiModelProperty("客户联系方式")
    private String phone;
    @ApiModelProperty("客户地址")
    private String address;
    @ApiModelProperty("客户邮箱")
    private String email;
//    @ApiModelProperty("客户创建时间")
//    @CreationTimestamp
//    private Date createTime;
//    @ApiModelProperty("客户更新时间")
//    @UpdateTimestamp
//    private Date updateTime;
    @ApiModelProperty("状态")
    private byte status;
    @ApiModelProperty("用户ID")
    private String userId;
}
