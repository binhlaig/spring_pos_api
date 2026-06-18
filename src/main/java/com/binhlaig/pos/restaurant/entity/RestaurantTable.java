package com.binhlaig.pos.restaurant.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "restaurant_tables", indexes = {
        @Index(name = "idx_restaurant_tables_shop_id", columnList = "shop_id"),
        @Index(name = "idx_restaurant_tables_shop_code", columnList = "shop_code"),
        @Index(name = "idx_restaurant_tables_status", columnList = "status")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "table_no", nullable = false, length = 50)
    private String tableNo;

    @Column(name = "table_name", length = 120)
    private String tableName;

    @Column
    private Integer seats;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private RestaurantTableStatus status;

    @Column(name = "floor_name", length = 120)
    private String floorName;

    @Column(columnDefinition = "TEXT")
    private String note;

    @Column(name = "shop_id", nullable = false)
    private Long shopId;

    @Column(name = "shop_code", nullable = false, length = 100)
    private String shopCode;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
        if (status == null) {
            status = RestaurantTableStatus.FREE;
        }
        clean();
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
        clean();
    }

    private void clean() {
        tableNo = cleanRequired(tableNo);
        tableName = blankToNull(tableName);
        floorName = blankToNull(floorName);
        note = blankToNull(note);
        shopCode = cleanRequired(shopCode);
    }

    private String cleanRequired(String value) {
        return value == null ? null : value.trim();
    }

    private String blankToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
