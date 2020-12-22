package com.dgut.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.dgut.dto.AdminDto;
import com.dgut.dto.UserRegisterDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
@ApiModel("管理员")
// jpa
@Entity(name = "admin")
@Table
@Data
@TableName(value = "sale_admin")
public class AdminEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("管理员ID")
    @TableId(type = IdType.AUTO)
    private Integer adminId;
    @ApiModelProperty("管理员名称")
    private String name;
    @ApiModelProperty("管理员联系方式")
    private String phone;
    @ApiModelProperty("管理员邮箱")
    private String email;
//    @ApiModelProperty("创建时间")
//    @CreationTimestamp
//    @TableField(fill = FieldFill.INSERT)
//    private Date createTime;
//    @ApiModelProperty("更新时间")
//    @UpdateTimestamp
//    @TableField(fill = FieldFill.INSERT_UPDATE)
//    private Date updateTime;
    @ApiModelProperty("管理员状态")
    private byte status;
    @ApiModelProperty("用户ID")
    private String userId;
    @ApiModelProperty(value = "管理员类别", notes = "0超级管理员1销售管理员2仓库管理员")
    private byte state;
    public AdminEntity(AdminDto adminDto){
        Optional.ofNullable(adminDto).ifPresent(item->{
            BeanUtils.copyProperties(item,this);
        });
    }
    public AdminEntity() {}
}
