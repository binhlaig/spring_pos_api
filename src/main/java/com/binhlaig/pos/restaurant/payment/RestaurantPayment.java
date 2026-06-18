package com.binhlaig.pos.restaurant.payment;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "restaurant_payments", indexes = {
        @Index(name = "idx_restaurant_payments_shop_id", columnList = "shop_id"),
        @Index(name = "idx_restaurant_payments_shop_code", columnList = "shop_code"),
        @Index(name = "idx_restaurant_payments_status", columnList = "status"),
        @Index(name = "idx_restaurant_payments_created_at", columnList = "created_at")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private RestaurantOrder order;

    @Column(name = "payment_no", nullable = false, unique = true, length = 40)
    private String paymentNo;

    @Column(name = "payment_method", nullable = false, length = 50)
    private String paymentMethod;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(name = "cash_received", precision = 12, scale = 2)
    private BigDecimal cashReceived;

    @Column(name = "change_amount", precision = 12, scale = 2)
    private BigDecimal changeAmount;

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
        paymentNo = trim(paymentNo);
        paymentMethod = trim(paymentMethod);
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
