package com.binhlaig.pos.admin;

import com.binhlaig.pos.admin.dto.EffectiveLimitsResponse;
import com.binhlaig.pos.admin.dto.ShopPlanOverrideResponse;
import com.binhlaig.pos.admin.dto.ShopUsageResponse;
import com.binhlaig.pos.admin.dto.UsageLimitUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class ShopUsageService {

    private final PlanLimitService planLimitService;
    private final ShopPlanOverrideRepository overrideRepository;

    @Transactional
    public ShopUsageResponse getUsage(Long shopId) {
        Shop shop = planLimitService.findShop(shopId);
        SubscriptionPlan plan = planLimitService.getCurrentPlanForShop(shopId);
        ShopUsageMonthly usage = planLimitService.getCurrentUsage(shopId);
        return toResponse(shop, plan, usage);
    }

    @Transactional
    public ShopUsageResponse updateUsageLimit(Long shopId, UsageLimitUpdateRequest request) {
        if (request == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usage limit request is required");
        }

        Shop shop = planLimitService.findShop(shopId);
        ShopPlanOverride override = overrideRepository.findByShopIdAndActiveTrue(shopId)
                .orElseGet(() -> overrideRepository.findByShopId(shopId)
                        .orElseGet(() -> ShopPlanOverride.builder()
                                .shopId(shopId)
                                .active(true)
                                .build()));

        override.setMaxStaff(request.maxStaff());
        override.setMaxProducts(request.maxProducts());
        override.setMaxReceiptsPerMonth(request.maxReceiptsPerMonth());
        override.setMaxStorageMb(request.maxStorageMb());
        override.setMaxDevices(request.maxDevices());
        override.setNote(cleanNullable(request.note()));
        override.setActive(true);
        override.setUpdatedAt(OffsetDateTime.now());
        overrideRepository.save(override);

        return getUsage(shop.getId());
    }

    @Transactional
    public ShopUsageResponse refreshUsage(Long shopId) {
        planLimitService.refreshUsage(shopId);
        return getUsage(shopId);
    }

    @Transactional
    public ShopUsageResponse resetUsage(Long shopId) {
        planLimitService.resetCurrentUsage(shopId);
        return getUsage(shopId);
    }

    private ShopUsageResponse toResponse(Shop shop, SubscriptionPlan plan, ShopUsageMonthly usage) {
        EffectiveLimitsResponse limits = planLimitService.getEffectiveLimits(shop.getId());
        ShopPlanOverride override = planLimitService.getActiveOverride(shop.getId());
        return new ShopUsageResponse(
                shop.getId(),
                shop.getShopCode(),
                shop.getShopName(),
                plan.getCode(),
                shop.getStatus() == null ? null : shop.getStatus().name(),
                shop.getSubscriptionEndDate(),
                limits,
                new ShopUsageResponse.Usage(
                        usage.getStaffCount(),
                        usage.getProductCount(),
                        usage.getReceiptCount(),
                        usage.getStorageUsedMb(),
                        usage.getDeviceCount()
                ),
                ShopPlanOverrideResponse.from(override)
        );
    }

    private String cleanNullable(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
