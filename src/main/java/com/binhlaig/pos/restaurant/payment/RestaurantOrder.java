package com.binhlaig.pos.restaurant.payment;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurant_orders", indexes = {
        @Index(name = "idx_restaurant_orders_shop_id", columnList = "shop_id"),
        @Index(name = "idx_restaurant_orders_shop_code", columnList = "shop_code"),
        @Index(name = "idx_restaurant_orders_status", columnList = "status"),
        @Index(name = "idx_restaurant_orders_created_at", columnList = "created_at")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_no", nullable = false, unique = true, length = 40)
    private String orderNo;

    @Column(name = "order_type", nullable = false, length = 50)
    private String orderType;

    @Column(name = "table_id")
    private Long tableId;

    @Column(name = "table_no", length = 50)
    private String tableNo;

    @Column(name = "staff_id", length = 100)
    private String staffId;

    @Column(name = "staff_name", length = 255)
    private String staffName;

    @Column(precision = 12, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "service_charge", precision = 12, scale = 2)
    private BigDecimal serviceCharge;

    @Column(precision = 12, scale = 2)
    private BigDecimal tax;

    @Column(precision = 12, scale = 2)
    private BigDecimal discount;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal total;

    @Column(nullable = false, length = 30)
    private String status;

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

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<RestaurantOrderItem> items = new ArrayList<>();

    public void addItem(RestaurantOrderItem item) {
        items.add(item);
        item.setOrder(this);
    }

    public void replaceItems(List<RestaurantOrderItem> replacementItems) {
        items.clear();
        replacementItems.forEach(this::addItem);
    }

    @PrePersist
    void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
        if (status == null) {
            status = "PAID";
        }
        clean();
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
        clean();
    }

    private void clean() {
        orderNo = trim(orderNo);
        orderType = trim(orderType);
        tableNo = blankToNull(tableNo);
        staffId = blankToNull(staffId);
        staffName = blankToNull(staffName);
        status = trim(status);
        note = blankToNull(note);
        shopCode = trim(shopCode);
    }

    private String trim(String value) {
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
