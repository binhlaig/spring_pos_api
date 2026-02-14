package com.binhlaig.pos.auth.dto;

public record AuthResponse(
        String token,
        String tokenType
) {}
