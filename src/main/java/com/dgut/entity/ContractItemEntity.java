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
@ApiModel("合同-商品项目表")
// jpa
@Entity(name = "contract_item")
@Table
@Data
@TableName(value = "sale_contract_item")
public class ContractItemEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("合同-商品项ID")
    @TableId(type = IdType.AUTO)
    private Integer contractItemId;
    @ApiModelProperty("总下单数量")
    private Integer count;
    @ApiModelProperty("剩余可下单数量")
    private Integer leaveCount;
    @ApiModelProperty("商品单价")
    private double perPrice;
    @ApiModelProperty("商品总价")
    private double price;
    @ApiModelProperty("合同ID")
    private Integer contractId;
    @ApiModelProperty("商品ID")
    private Integer goodsId;
//    @ApiModelProperty("创建时间")
//    @CreationTimestamp
//    private Date createTime;
//    @ApiModelProperty("更新时间")
//    @UpdateTimestamp
//    private Date updateTime;
}
