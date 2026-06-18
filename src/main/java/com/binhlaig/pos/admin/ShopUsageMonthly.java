package com.binhlaig.pos.admin;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(
        name = "shop_usage_monthly",
        uniqueConstraints = @UniqueConstraint(name = "uk_shop_usage_monthly_shop_year_month", columnNames = {"shop_id", "year", "month"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopUsageMonthly {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shop_id", nullable = false)
    private Long shopId;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Integer month;

    @Column(name = "staff_count", nullable = false)
    private Integer staffCount;

    @Column(name = "product_count", nullable = false)
    private Integer productCount;

    @Column(name = "receipt_count", nullable = false)
    private Integer receiptCount;

    @Column(name = "storage_used_mb", nullable = false)
    private Integer storageUsedMb;

    @Column(name = "device_count", nullable = false)
    private Integer deviceCount;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    @PreUpdate
    void touch() {
        if (staffCount == null) staffCount = 0;
        if (productCount == null) productCount = 0;
        if (receiptCount == null) receiptCount = 0;
        if (storageUsedMb == null) storageUsedMb = 0;
        if (deviceCount == null) deviceCount = 0;
        updatedAt = OffsetDateTime.now();
    }
}
