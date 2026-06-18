package com.binhlaig.pos.admin;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "shop_plan_overrides")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopPlanOverride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shop_id", nullable = false, unique = true)
    private Long shopId;

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

    @Column(columnDefinition = "text")
    private String note;

    @Column(nullable = false)
    private Boolean active;

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
}
