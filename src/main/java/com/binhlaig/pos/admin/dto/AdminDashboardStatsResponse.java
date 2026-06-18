package com.binhlaig.pos.admin.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record AdminDashboardStatsResponse(
        long totalShops,
        long activeShops,
        long expiredShops,
        long suspendedShops,
        long totalUsers,
        BigDecimal todaySales,
        BigDecimal monthlyRevenue
) {
}
