package com.binhlaig.pos.shop;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "shop_settings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shop_id", nullable = false, unique = true)
    private Long shopId;

    @Column(name = "shop_code")
    private String shopCode;

    @Column(name = "shop_name")
    private String shopName;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "currency_code", nullable = false)
    private String currencyCode;

    @Column(name = "currency_symbol", nullable = false)
    private String currencySymbol;

    @Column(name = "currency_decimal_digits", nullable = false)
    private Integer currencyDecimalDigits;

    @Column(name = "currency_position", nullable = false)
    private String currencyPosition;

    @Column(name = "tax_percent", nullable = false)
    private BigDecimal taxPercent;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @PrePersist
    void onCreate() {
        OffsetDateTime now = OffsetDateTime.now();

        if (currencyCode == null || currencyCode.isBlank()) {
            currencyCode = "MMK";
        }

        if (currencySymbol == null || currencySymbol.isBlank()) {
            currencySymbol = "Ks";
        }

        if (currencyDecimalDigits == null) {
            currencyDecimalDigits = 0;
        }

        if (currencyPosition == null || currencyPosition.isBlank()) {
            currencyPosition = "BEFORE";
        }

        if (taxPercent == null) {
            taxPercent = BigDecimal.ZERO;
        }

        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}