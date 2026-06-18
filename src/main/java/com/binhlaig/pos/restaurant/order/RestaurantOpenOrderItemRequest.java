package com.binhlaig.pos.restaurant.order;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class RestaurantOpenOrderItemRequest {

    @JsonAlias({"productId", "product_id"})
    private Long productId;

    @JsonAlias({"itemName", "item_name"})
    private String itemName;

    private Integer quantity;

    @JsonAlias({"unitPrice", "unit_price"})
    private BigDecimal unitPrice;

    @JsonAlias({"totalPrice", "total_price"})
    private BigDecimal totalPrice;

    private List<String> modifiers;

    @JsonAlias({"kitchenNote", "kitchen_note"})
    private String kitchenNote;
}
