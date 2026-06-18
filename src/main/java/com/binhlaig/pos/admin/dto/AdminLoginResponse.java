package com.binhlaig.pos.admin.dto;

import lombok.Builder;

@Builder
public record AdminLoginResponse(
        Long id,
        String username,
        String email,
        String role,
        String accessToken
) {
}
