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

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@ApiModel("采购清单-发货单数据表")
// jpa
@Entity(name = "purchase_item")
@Table
@Data
@TableName(value = "sale_purchase_item")
public class PurchaseItemEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("发货单ID")
    @TableId(type = IdType.AUTO)
    private Integer purchaseItemId;
    @ApiModelProperty("商品数目")
    private Integer count;
    @ApiModelProperty("该类商品单价（合同中）")
    private Double perPrice;
    @ApiModelProperty("总价（单价X数量）")
    private Double price;
//    @ApiModelProperty("创建时间")
//    @CreationTimestamp
//    private Date createTime;
    @ApiModelProperty("采购清单ID")
    private Integer purchaseId;
    @ApiModelProperty("商品ID")
    private Integer goodsId;
}
