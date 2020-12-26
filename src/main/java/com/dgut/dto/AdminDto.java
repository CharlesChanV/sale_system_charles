package com.dgut.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AdminDto {
    private String name;
    private String phone;
    private String email;
    private String address;
    private Byte status = 1;
    private String userId;
    private Byte state;
}
