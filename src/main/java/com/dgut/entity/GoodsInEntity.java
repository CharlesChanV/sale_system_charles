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
@ApiModel("商品数据表")
// jpa
@Entity(name = "goods_in")
@Table
@Data
@TableName(value = "sale_goods_in")
public class GoodsInEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "进货单ID")
    @TableId(type = IdType.AUTO)
    private Integer goodsInId;
    @ApiModelProperty("商品ID")
    private Integer goodsId;
    @ApiModelProperty("进货数量")
    private Integer count;
    @ApiModelProperty(value = "进货状态", notes = "0未进货1已进货")
    @Column(columnDefinition="tinyint default 0 comment '0未进货1已进货'")
    private byte status;
    @ApiModelProperty("备注")
    private String remark;
//    @ApiModelProperty("创建时间")
//    @CreationTimestamp
//    private Date createTime;
//    @ApiModelProperty("更新时间")
//    @UpdateTimestamp
//    private Date updateTime;
    @ApiModelProperty("管理员ID")
    private Integer adminId;
    @ApiModelProperty("物流ID")
    private Integer logisticsId;
}
