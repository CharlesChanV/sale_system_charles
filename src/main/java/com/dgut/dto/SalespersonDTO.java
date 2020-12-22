package com.dgut.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;

@Data
public class SalespersonDTO {
    private String name;
    private String phone;
    private String email;
//    @Column(name="status",columnDefinition="tinyint default 1 comment '状态1正常0禁用'")
    private Byte status;
    private String userId;
}
