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
@ApiModel("合同数据表")
// jpa
@Entity(name = "contract")
@Table
@Data
@TableName(value = "sale_contract")
public class ContractEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("合同ID")
    @TableId(type = IdType.AUTO)
    private Integer contractId;
    @ApiModelProperty("合同编号")
    private String contractNo;
    @ApiModelProperty("合同主题")
    private String contractName;
    @ApiModelProperty("合同总额")
    private double contractPrice;
    @ApiModelProperty("合同甲方")
    private String firstParty;
    @ApiModelProperty("合同乙方")
    private String secondParty;
    @ApiModelProperty("签订日期")
    private Date signDateTime;
    @ApiModelProperty("开始日期")
    private Date startDate;
    @ApiModelProperty("结束日期")
    private Date endDate;
//    @ApiModelProperty("创建时间")
//    @CreationTimestamp
//    private Date createTime;
//    @ApiModelProperty("更新时间")
//    @UpdateTimestamp
//    private Date updateTime;
    @ApiModelProperty("来自用户名称")
    private String fromName;
//  0合同拟定
//	1合同签订
//	2合同履行
//	3结算
//	4中止
    @ApiModelProperty(value = "合同状态", notes = "0合同拟定1合同签订2合同履行3结算4中止")
    @Column(name="status",columnDefinition="tinyint default 0 comment '0合同拟定1合同签订2合同履行3结算4中止'")
    private byte status;
    @ApiModelProperty("客户ID")
    private Integer customerId;
    @ApiModelProperty("销售人员ID")
    private Integer salespersonId;
}
