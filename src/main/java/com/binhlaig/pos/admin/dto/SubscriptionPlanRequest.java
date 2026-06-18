package com.binhlaig.pos.admin.dto;

import java.math.BigDecimal;

public record SubscriptionPlanRequest(
        String code,
        String name,
        BigDecimal priceMonthly,
        Integer maxStaff,
        Integer maxProducts,
        Integer maxReceiptsPerMonth,
        Integer maxStorageMb,
        Integer maxDevices,
        Integer maxBranches,
        Boolean allowRestaurant,
        Boolean allowFashion,
        Boolean allowAnalytics,
        Boolean allowKitchen,
        Boolean allowTableOrder,
        Boolean active
) {
}
