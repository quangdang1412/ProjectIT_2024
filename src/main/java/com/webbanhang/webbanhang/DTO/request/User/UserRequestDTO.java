package com.webbanhang.webbanhang.DTO.request.User;
import com.webbanhang.webbanhang.Util.PhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.io.Serializable;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDTO implements Serializable {

    @NotBlank(message = "UserID must be not blank")
    @NotNull
    private String userID;

    @NotBlank(message = "UserName must be not blank")
    @NotNull
    private String userName;

    @NotBlank(message = "Password must be not blank")
    @NotNull
    private String password;

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

    @NotNull(message = "Type must be not null")
    private Integer type;

}
