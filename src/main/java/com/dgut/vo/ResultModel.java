package com.dgut.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultModel implements Serializable{

    private int error;

    private String message;

    private String openid;

    private String access_token;

}
