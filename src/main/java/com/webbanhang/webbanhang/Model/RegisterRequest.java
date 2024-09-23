package com.webbanhang.webbanhang.Model;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
  private String name;
  private String email;
  private String password;
}