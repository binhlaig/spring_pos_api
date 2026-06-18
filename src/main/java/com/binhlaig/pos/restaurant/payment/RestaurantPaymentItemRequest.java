package com.binhlaig.pos.restaurant.payment;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class RestaurantPaymentItemRequest {

    @JsonAlias({"productId", "dbId", "id", "product_id"})
    private Long productId;

    @JsonAlias({"itemName", "item_name", "productName", "product_name"})
    private String itemName;

    @JsonAlias({"qty"})
    private Integer quantity;

    @JsonAlias({"unitPrice", "unit_price"})
    private BigDecimal unitPrice;

    @JsonAlias({"totalPrice", "total_price"})
    private BigDecimal totalPrice;

    private List<String> modifiers;

    @JsonAlias({"kitchenNote", "kitchen_note"})
    private String kitchenNote;
}
