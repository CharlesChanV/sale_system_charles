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
@ApiModel("采购清单数据表")
// jpa
@Entity(name = "purchase")
@Table
@Data
@TableName(value = "sale_purchase")
public class PurchaseEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("采购清单ID")
    @TableId(type = IdType.AUTO)
    private Integer purchaseId;
    @ApiModelProperty("采购清单编号")
    private String purchaseNo;
    @ApiModelProperty("支付状态")
    private byte payStatus;
    @ApiModelProperty("总价")
    private Double totalPrice;
//    @ApiModelProperty("发货状态")
    @ApiModelProperty(value = "发货状态", notes = "0未发货1已发货")
    @Column(columnDefinition="tinyint default 0 comment '0未发货1已发货'")
    private Integer deliverStatus;
//    @ApiModelProperty("创建时间")
//    @CreationTimestamp
//    private Date createTime;
    @ApiModelProperty("合同ID")
    private Integer contractId;
    @ApiModelProperty("物流ID")
    private Integer logisticsId;
    @ApiModelProperty("客户ID")
    private Integer customerId;
    @ApiModelProperty("用户ID")
    private String userId;
    @ApiModelProperty("仓库管理员ID")
    private Integer adminId;
}
