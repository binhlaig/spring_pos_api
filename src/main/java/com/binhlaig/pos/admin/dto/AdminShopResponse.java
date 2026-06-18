package com.binhlaig.pos.admin.dto;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public record AdminShopResponse(
        Long id,
        String shopCode,
        String shopName,
        String address,
        String businessType,
        String status,
        String subscriptionPlan,
        LocalDate subscriptionStartDate,
        LocalDate subscriptionEndDate,
        String suspendedReason,
        OffsetDateTime suspendedAt,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
