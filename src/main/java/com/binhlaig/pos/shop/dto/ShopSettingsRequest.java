package com.binhlaig.pos.shop.dto;

import java.math.BigDecimal;

public record ShopSettingsRequest(
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