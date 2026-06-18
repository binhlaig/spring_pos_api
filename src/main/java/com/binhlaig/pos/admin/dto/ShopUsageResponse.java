package com.binhlaig.pos.admin.dto;

import java.time.LocalDate;

public record ShopUsageResponse(
        Long shopId,
        String shopCode,
        String shopName,
        String planCode,
        String status,
        LocalDate subscriptionEndDate,
        EffectiveLimitsResponse limits,
        Usage usage,
        ShopPlanOverrideResponse override
) {
    public record Usage(
            Integer staffCount,
            Integer productCount,
            Integer receiptCount,
            Integer storageUsedMb,
            Integer deviceCount
    ) {
    }
}
