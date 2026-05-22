package com.binhlaig.pos.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private String tokenType;

    private String username;
    private String role;

    private Long shopId;
    private String shopCode;

    // ✅ ADD THIS (important for staff)
    private Long staffId;

    private String imageUrl;
}