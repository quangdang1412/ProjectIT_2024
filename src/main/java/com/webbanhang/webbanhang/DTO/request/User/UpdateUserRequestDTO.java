package com.webbanhang.webbanhang.DTO.request.User;

import com.webbanhang.webbanhang.Util.PhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UpdateUserRequestDTO implements Serializable {
    @NotBlank(message = "UserID must be not blank")
    @NotNull
    private String userID;

    @NotBlank(message = "UserName must be not blank")
    @NotNull
    private String userName;


    @Email(message = "email invalid format")
    @NotBlank(message = "email must be not blank")
    @NotNull
    private String email;

    @PhoneNumber(message = "phone invalid format")
    private String phone;

    @Pattern(regexp = "^Nam|Nữ$", message = "gender must be one in {Nam,Nữ}")
    private String gender;

    @NotBlank(message = "Address must be not blank")
    private String address;

}
