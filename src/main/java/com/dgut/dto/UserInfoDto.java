package com.dgut.dto;

import com.dgut.entity.UserEntity;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;

import javax.persistence.Column;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class UserInfoDto {

    private String userId;

    private String username;

    private String password;

    public UserInfoDto(UserEntity users){
        Optional.ofNullable(users).ifPresent(user->{
            BeanUtils.copyProperties(user,this);
        });
    }
}
