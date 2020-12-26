package com.dgut.dto;

import lombok.Data;

@Data
public class UserRegisterDTO {
    private String username;
    private String password;
    private String avatar = "https://i.picsum.photos/id/398/300/300.jpg?hmac=O1w_nanHaJa7EYRgKbnc_XQif4j9oSQKyAa5D2oCGm4";
}
