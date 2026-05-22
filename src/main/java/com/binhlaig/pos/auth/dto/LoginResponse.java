package com.binhlaig.pos.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
    private String token;
    private String tokenType;
    private String username;
    private String role;
    private Long shopId;
    private String shopCode;
    private String staffId;
    private String imageUrl;
}