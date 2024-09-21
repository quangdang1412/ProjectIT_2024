package com.webbanhang.webbanhang.DTO.request.User;

import com.webbanhang.webbanhang.Util.PhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpRequestDTO implements Serializable {

    @NotBlank(message = "UserName must be not blank")
    @NotNull
    private String userName;

    @PhoneNumber(message = "phone invalid format")
    private String phone;

    @NotBlank(message = "Password must be not blank")
    @NotNull
    private String password;

    @Email(message = "email invalid format")
    @NotBlank(message = "email must be not blank")
    @NotNull
    private String email;
}
