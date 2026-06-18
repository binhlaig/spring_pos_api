package com.binhlaig.pos.admin;

import com.binhlaig.pos.admin.dto.ShopUsageResponse;
import com.binhlaig.pos.admin.dto.UsageLimitUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/shops")
public class AdminShopUsageController {

    private final ShopUsageService shopUsageService;

    @GetMapping("/{shopId}/usage")
    public ShopUsageResponse getUsage(@PathVariable Long shopId) {
        return shopUsageService.getUsage(shopId);
    }

    @PutMapping("/{shopId}/usage-limit")
    public ShopUsageResponse updateUsageLimit(
            @PathVariable Long shopId,
            @RequestBody UsageLimitUpdateRequest request
    ) {
        return shopUsageService.updateUsageLimit(shopId, request);
    }

    @PostMapping("/{shopId}/usage/refresh")
    public ShopUsageResponse refreshUsage(@PathVariable Long shopId) {
        return shopUsageService.refreshUsage(shopId);
    }

    @PostMapping("/{shopId}/usage/reset")
    public ShopUsageResponse resetUsage(@PathVariable Long shopId) {
        return shopUsageService.resetUsage(shopId);
    }
}
