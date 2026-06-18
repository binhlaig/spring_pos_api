package com.binhlaig.pos.admin.dto;

public record AdminShopCheckResponse(
        boolean shopIdExists,
        boolean shopCodeExists,
        Long suggestedShopId,
        String suggestedShopCode
) {
}
