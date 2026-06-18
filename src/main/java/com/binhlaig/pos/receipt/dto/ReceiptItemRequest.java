package com.binhlaig.pos.receipt.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceiptItemRequest {
    @JsonAlias({"dbId", "id", "product_id"})
    private String productId;
    private String barcode;
    private String sku;

    @JsonAlias({"product_name"})
    private String productName;

    @JsonAlias({"quantity"})
    private Integer qty;
    private BigDecimal price;
    private BigDecimal discountPercent;
    private Boolean taxable;
    private BigDecimal lineTotal;
}
