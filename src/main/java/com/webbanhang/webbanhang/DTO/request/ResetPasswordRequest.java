package com.webbanhang.webbanhang.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordRequest implements Serializable {
    private String confirmLink;
    private String email;
}
