package com.binhlaig.pos.admin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AdminRegisterRequest(
        @NotBlank String username,
        @Email String email,
        @NotBlank String password
) {
}
