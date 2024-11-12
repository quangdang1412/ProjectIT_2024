package com.webbanhang.webbanhang.Model.PK;

import com.webbanhang.webbanhang.Model.ProductModel;
import com.webbanhang.webbanhang.Model.UserModel;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserCartID implements Serializable {
    private UserModel userCart;
    private ProductModel productCart;
}
