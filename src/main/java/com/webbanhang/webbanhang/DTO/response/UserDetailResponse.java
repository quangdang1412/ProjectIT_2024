package com.webbanhang.webbanhang.DTO.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class UserDetailResponse implements Serializable {
    private long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
}
