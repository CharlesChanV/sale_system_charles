package com.dgut.vo;

import com.dgut.entity.UserEntity;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Optional;

@Data
public class UserInfoVO {
    private String userId;
    private String username;

//    public UserInfoVO(UserEntity users){
//        Optional.ofNullable(users).ifPresent(user->{
//            BeanUtils.copyProperties(user,this);
//        });
//    }

}
