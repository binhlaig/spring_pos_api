package com.binhlaig.pos.admin;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "shops")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shop {

    @Id
    private Long id;

    @Column(name = "shop_code", nullable = false, unique = true, length = 50)
    private String shopCode;

    @Column(name = "shop_name", nullable = false, length = 180)
    private String shopName;

    @Column(columnDefinition = "text")
    private String address;

    @Column(name = "business_type", nullable = false, length = 50)
    private String businessType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ShopStatus status;

    @Column(name = "subscription_plan", nullable = false, length = 50)
    private String subscriptionPlan;

    @Column(name = "subscription_start_date")
    private LocalDate subscriptionStartDate;

    @Column(name = "subscription_end_date")
    private LocalDate subscriptionEndDate;

    @Column(name = "suspended_reason", columnDefinition = "text")
    private String suspendedReason;

    @Column(name = "suspended_at")
    private OffsetDateTime suspendedAt;

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
}
