package com.binhlaig.pos.admin.dto;

public record EffectiveLimitsResponse(
        Integer maxStaff,
        Integer maxProducts,
        Integer maxReceiptsPerMonth,
        Integer maxStorageMb,
        Integer maxDevices,
        Integer maxBranches
) {
}
