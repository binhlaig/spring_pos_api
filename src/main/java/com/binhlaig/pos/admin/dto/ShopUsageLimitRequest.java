package com.binhlaig.pos.admin.dto;

public record ShopUsageLimitRequest(
        Integer maxStaff,
        Integer maxProducts,
        Integer maxReceiptsPerMonth,
        Integer maxStorageMb,
        Integer maxDevices,
        String note
) {
}
