package com.dgut.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class ContractDTO {
    private Integer contractId;
    private String contractNo;
    private String contractName;
    private Double contractPrice;
    private String firstParty;
    private String secondParty;
    private Date signDateTime;
    private Date startDate;
    private Date endDate;
    private Byte status;
    private Integer customerId;
    private Integer salespersonId;
}
