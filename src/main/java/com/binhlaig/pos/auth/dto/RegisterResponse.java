package com.binhlaig.pos.auth.dto;

public record RegisterResponse(
        String access_token,
        String username,
        String role,
        String avatarPath
) {}
