package com.binhlaig.pos.receipt.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "pos_receipt_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PosReceiptItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_id", nullable = false)
    private PosReceipt receipt;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "sku")
    private String sku;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(nullable = false)
    private Integer qty;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    @Column(name = "discount_percent", nullable = false, precision = 5, scale = 2)
    private BigDecimal discountPercent;

    @Column(nullable = false)
    private Boolean taxable;

    @Column(name = "line_total", nullable = false, precision = 15, scale = 2)
    private BigDecimal lineTotal;

    @PrePersist
    void prePersist() {
        if (qty == null) qty = 1;
        if (price == null) price = BigDecimal.ZERO;
        if (discountPercent == null) discountPercent = BigDecimal.ZERO;
        if (taxable == null) taxable = true;
        if (lineTotal == null) lineTotal = BigDecimal.ZERO;
    }
}