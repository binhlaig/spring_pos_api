package com.binhlaig.pos.shopfeature;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "shop_features")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shop_id", nullable = false, unique = true)
    private Long shopId;

    @Column(name = "shop_code", nullable = false, length = 50)
    private String shopCode;

    @Builder.Default
    @Column(name = "dashboard_enabled")
    private Boolean dashboardEnabled = true;

    @Builder.Default
    @Column(name = "products_enabled")
    private Boolean productsEnabled = true;

    @Builder.Default
    @Column(name = "pos_register_enabled")
    private Boolean posRegisterEnabled = true;

    @Builder.Default
    @Column(name = "receipts_enabled")
    private Boolean receiptsEnabled = true;

    @Builder.Default
    @Column(name = "staff_enabled")
    private Boolean staffEnabled = false;

    @Builder.Default
    @Column(name = "tasks_enabled")
    private Boolean tasksEnabled = false;

    @Builder.Default
    @Column(name = "timecard_enabled")
    private Boolean timecardEnabled = false;

    @Builder.Default
    @Column(name = "restaurant_pos_enabled")
    private Boolean restaurantPosEnabled = false;

    @Builder.Default
    @Column(name = "restaurant_tables_enabled")
    private Boolean restaurantTablesEnabled = false;

    @Builder.Default
    @Column(name = "restaurant_kitchen_enabled")
    private Boolean restaurantKitchenEnabled = false;

    @Builder.Default
    @Column(name = "restaurant_orders_enabled")
    private Boolean restaurantOrdersEnabled = false;

    @Builder.Default
    @Column(name = "allow_restaurant")
    private Boolean allowRestaurant = true;

    @Builder.Default
    @Column(name = "allow_kitchen")
    private Boolean allowKitchen = true;

    @Builder.Default
    @Column(name = "allow_table_order")
    private Boolean allowTableOrder = true;

    @Builder.Default
    @Column(name = "settings_enabled")
    private Boolean settingsEnabled = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        if (createdAt == null) {
            createdAt = now;
        }
        updatedAt = now;
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
