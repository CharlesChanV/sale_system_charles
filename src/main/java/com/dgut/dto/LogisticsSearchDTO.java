package com.dgut.dto;

import com.dgut.entity.LogisticsEntity;
import com.dgut.entity.UserEntity;
import com.dgut.mapper.LogisticsMapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class LogisticsSearchDTO {
    private String logisticsNo;
    private String companyName;
    private Integer payMode;
    private Double fee;
    private String senderName;
    private String senderAddress;
    private String senderPhone;
    private String addresseeName;
    private String addresseeAddress;
    private String addresseePhone;
    public LogisticsSearchDTO(LogisticsEntity logisticsEntity){
        Optional.ofNullable(logisticsEntity).ifPresent(logistics->{
            BeanUtils.copyProperties(logistics,this);
        });
    }
}
