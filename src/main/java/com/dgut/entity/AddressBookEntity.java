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

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@ApiModel("地址表")
// jpa
@Entity(name = "address_book")
@Table
@Data
@TableName(value = "sale_address_book")
public class AddressBookEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("地址ID")
    @TableId(type = IdType.AUTO)
    private Integer addressId;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("联系方式")
    private String phone;
    @ApiModelProperty("城市")
    private String city;
    @ApiModelProperty("地址详情")
    private String addressDetail;
//    @ApiModelProperty("创建时间")
//    @CreationTimestamp
//    private Date createTime;
    @ApiModelProperty("用户ID")
    private String userId;
}
