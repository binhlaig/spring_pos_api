package com.binhlaig.pos.admin.dto;

import com.binhlaig.pos.admin.ShopPlanOverride;

public record ShopPlanOverrideResponse(
        Long id,
        Long shopId,
        Integer maxStaff,
        Integer maxProducts,
        Integer maxReceiptsPerMonth,
        Integer maxStorageMb,
        Integer maxDevices,
        String note,
        Boolean active
) {
    public static ShopPlanOverrideResponse from(ShopPlanOverride override) {
        if (override == null) {
            return null;
        }
        return new ShopPlanOverrideResponse(
                override.getId(),
                override.getShopId(),
                override.getMaxStaff(),
                override.getMaxProducts(),
                override.getMaxReceiptsPerMonth(),
                override.getMaxStorageMb(),
                override.getMaxDevices(),
                override.getNote(),
                override.getActive()
        );
    }
}
