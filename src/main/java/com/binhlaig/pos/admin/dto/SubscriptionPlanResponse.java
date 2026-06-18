package com.binhlaig.pos.admin.dto;

import com.binhlaig.pos.admin.SubscriptionPlan;

import java.math.BigDecimal;

public record SubscriptionPlanResponse(
        Long id,
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
        Boolean allowTableOrder
) {
    public static SubscriptionPlanResponse from(SubscriptionPlan plan) {
        return new SubscriptionPlanResponse(
                plan.getId(),
                plan.getCode(),
                plan.getName(),
                plan.getPriceMonthly(),
                plan.getMaxStaff(),
                plan.getMaxProducts(),
                plan.getMaxReceiptsPerMonth(),
                plan.getMaxStorageMb(),
                plan.getMaxDevices(),
                plan.getMaxBranches(),
                plan.getAllowRestaurant(),
                plan.getAllowFashion(),
                plan.getAllowAnalytics(),
                plan.getAllowKitchen(),
                plan.getAllowTableOrder()
        );
    }
}
