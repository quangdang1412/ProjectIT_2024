package com.webbanhang.webbanhang.DTO.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private int status;
}
