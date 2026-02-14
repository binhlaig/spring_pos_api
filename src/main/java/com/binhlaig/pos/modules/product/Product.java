package com.binhlaig.pos.modules.product;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "products", indexes = {
        @Index(name = "idx_products_sku", columnList = "sku", unique = true),
        @Index(name = "idx_products_barcode", columnList = "barcode")
})
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 64, unique = true)
    private String sku;

    /**
     * ✅ DB has NOT NULL column "name"
     * Keep it in-sync with productName
     */
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "product_name", nullable = false, length = 255)
    private String productName;

    @Column(name = "product_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal productPrice;

    @Column(name = "product_quantity_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal productQuantityAmount;

    @Column(length = 64)
    private String barcode;

    @Column(length = 64)
    private String category;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type", length = 32)
    private ProductType productType;

    @Column(name = "product_discount", nullable = false, precision = 12, scale = 2)
    private BigDecimal productDiscount;

    @Column(columnDefinition = "text")
    private String note;

    // uploaded image path (ex: /uploads/products/xxx.jpg)
    @Column(name = "image_path", length = 512)
    private String imagePath;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    void onCreate() {
        if (createdAt == null) createdAt = Instant.now();
        if (productQuantityAmount == null) productQuantityAmount = BigDecimal.ZERO;
        if (productDiscount == null) productDiscount = BigDecimal.ZERO;

        // ✅ ensure name/productName never null
        if (name == null || name.isBlank()) {
            name = productName;
        }
        if (productName == null || productName.isBlank()) {
            productName = name;
        }
    }

    @PreUpdate
    void onUpdate() {
        // ✅ keep in sync when updating too
        if (name == null || name.isBlank()) name = productName;
        if (productName == null || productName.isBlank()) productName = name;
    }
}
