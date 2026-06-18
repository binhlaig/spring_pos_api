package com.binhlaig.pos.admin.dto;

public record AdminShopRegisterResponse(
        String message,
        String username,
        String role,
        Long shopId,
        String shopCode,
        String shopName,
        String address
) {
}
