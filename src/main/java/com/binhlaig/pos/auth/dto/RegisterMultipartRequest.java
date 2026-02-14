package com.binhlaig.pos.auth.dto;

import com.binhlaig.pos.auth.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterMultipartRequest(
        @NotBlank String username,
        @NotBlank String password,
        @NotNull Role role
) {}
