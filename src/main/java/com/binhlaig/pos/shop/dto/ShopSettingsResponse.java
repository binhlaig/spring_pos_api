package com.binhlaig.pos.shop.dto;

import java.math.BigDecimal;

public record ShopSettingsResponse(
        Long shopId,
        String shopCode,
        String shopName,
        String address,
        String phone,

        String currencyCode,
        String currencySymbol,
        Integer currencyDecimalDigits,
        String currencyPosition,

        BigDecimal taxPercent
) {
}