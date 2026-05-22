package com.binhlaig.pos.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StaffLoginRequest {

    @NotBlank(message = "Shop code is required")
    private String shopCode;

    // ✅ FIX HERE
    @NotNull(message = "Staff ID is required")
    private Long staffId;

    @NotBlank(message = "Password is required")
    private String password;
}