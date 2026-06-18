package com.binhlaig.pos.admin;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "subscription_plans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "price_monthly", nullable = false, precision = 12, scale = 2)
    private BigDecimal priceMonthly;

    @Column(name = "max_staff")
    private Integer maxStaff;

    @Column(name = "max_products")
    private Integer maxProducts;

    @Column(name = "max_receipts_per_month")
    private Integer maxReceiptsPerMonth;

    @Column(name = "max_storage_mb")
    private Integer maxStorageMb;

    @Column(name = "max_devices")
    private Integer maxDevices;

    @Column(name = "max_branches")
    private Integer maxBranches;

    @Column(name = "allow_restaurant", nullable = false)
    private Boolean allowRestaurant;

    @Column(name = "allow_fashion", nullable = false)
    private Boolean allowFashion;

    @Column(name = "allow_analytics", nullable = false)
    private Boolean allowAnalytics;

    @Column(name = "allow_kitchen", nullable = false)
    private Boolean allowKitchen;

    @Column(name = "allow_table_order", nullable = false)
    private Boolean allowTableOrder;

    @Column(nullable = false)
    private Boolean active;

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
}
