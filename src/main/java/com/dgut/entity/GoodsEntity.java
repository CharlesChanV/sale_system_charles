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
@Entity(name = "goods")
@Table
@Data
@TableName(value = "sale_goods")
public class GoodsEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("商品ID")
    @TableId(type = IdType.AUTO)
    private Integer goodsId;
    @ApiModelProperty("商品名称")
    private String name;
    @ApiModelProperty("商品描述")
    private String description;
    @ApiModelProperty("商品商品单价")
    private Double perPrice;
    @ApiModelProperty("商品库存")
    private Integer stock;
    @ApiModelProperty(value = "商品状态", notes = "0下架1上架")
    @Column(name="status",columnDefinition="tinyint default 0 comment '0下架1上架'")
    private Byte status;
//    @ApiModelProperty("创建时间")
//    @CreationTimestamp
//    private Date createTime;
//    @ApiModelProperty("更新时间")
//    @UpdateTimestamp
//    private Date updateTime;
//    @ApiModelProperty("删除时间")
//    private Date deleteTime;
}
