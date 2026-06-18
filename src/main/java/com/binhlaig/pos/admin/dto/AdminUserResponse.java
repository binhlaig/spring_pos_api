package com.binhlaig.pos.admin.dto;

import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record AdminUserResponse(
        Long id,
        String username,
        String email,
        String role,
        Boolean active,
        OffsetDateTime createdAt,
        OffsetDateTime lastLoginAt
) {
}
