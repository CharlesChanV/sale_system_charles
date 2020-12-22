package com.dgut.dto;


import com.dgut.entity.AddressBookEntity;
import com.dgut.entity.UserEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class AddressBookDTO {
//    private int addressId;
    private String name;
    private String phone;
    private String city;
    private String addressDetail;
    private String userId;
    public AddressBookDTO(AddressBookEntity addressBookEntity){
        Optional.ofNullable(addressBookEntity).ifPresent(item->{
            BeanUtils.copyProperties(item,this);
        });
    }
}
