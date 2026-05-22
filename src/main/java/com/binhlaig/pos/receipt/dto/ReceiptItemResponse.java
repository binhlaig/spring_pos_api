package com.binhlaig.pos.receipt.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceiptItemResponse {
    private Long id;
    private String productId;
    private String barcode;
    private String sku;
    private String productName;
    private Integer qty;
    private BigDecimal price;
    private BigDecimal discountPercent;
    private Boolean taxable;
    private BigDecimal lineTotal;
}