package com.dgut.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dgut.dto.UserRegisterDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
@ApiModel("用户信息表")
// jpa
@Entity(name = "user")
@Table
@Data
@TableName(value = "sale_user")
public class UserEntity extends BaseEntity {
    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    @ApiModelProperty("用户ID")
    @TableId(type = IdType.ASSIGN_UUID)
    private String userId;
    @ApiModelProperty("用户用户名")
    private String username;
    @ApiModelProperty("用户密码")
    private String password;
//    @ApiModelProperty("角色权限")
//    @Column(name="roles",columnDefinition="varchar(100) default 'ROLE_user' COMMENT '普通用户:ROLE_user;销售管理员:ROLE_sales_admin;仓库管理员:ROLE_store_admin'")
//    private String roles = "ROLE_user";
    public UserEntity(UserRegisterDTO userRegisterDTO){
        Optional.ofNullable(userRegisterDTO).ifPresent(item->{
            BeanUtils.copyProperties(item,this);
        });
    }

    public UserEntity() {

    }
}
