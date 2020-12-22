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
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@ApiModel("地址表")
// jpa
@Entity(name = "logistics")
@Table
@Data
@TableName(value = "sale_logistics")
public class LogisticsEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("物流ID")
    @TableId(type = IdType.AUTO)
    private Integer logisticsId;
    @ApiModelProperty("物流编号")
    private String logisticsNo;
    @ApiModelProperty("公司名称")
    private String companyName;
    @ApiModelProperty("物流支付方式")
    private Integer payMode;
    @ApiModelProperty("物流费用")
    private Double fee;
    @ApiModelProperty("发件人姓名")
    private String senderName;
    @ApiModelProperty("发件人地址")
    private String senderAddress;
    @ApiModelProperty("发件人联系方式")
    private String senderPhone;
    @ApiModelProperty("收件人姓名")
    private String addresseeName;
    @ApiModelProperty("收件人地址")
    private String addresseeAddress;
    @ApiModelProperty("收件人联系方式")
    private String addresseePhone;
//    @ApiModelProperty("创建时间")
//    @CreationTimestamp
//    private Date createTime;
//    @ApiModelProperty("更新时间")
//    @UpdateTimestamp
//    private Date updateTime;
}
