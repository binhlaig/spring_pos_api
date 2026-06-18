package com.binhlaig.pos.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class KitchenTicketItemRequest {

    @JsonAlias({"menuItemId", "menu_item_id", "productId", "product_id", "id"})
    private Long menuItemId;

    @JsonAlias({"itemName", "item_name", "name"})
    @NotBlank(message = "itemName is required")
    @Size(max = 255, message = "itemName must be less than 255 characters")
    private String itemName;

    @JsonAlias({"quantity", "qty"})
    @Min(value = 1, message = "quantity must be at least 1")
    private Integer quantity;

    @JsonAlias({"unitPrice", "unit_price", "price"})
    private BigDecimal unitPrice;

    private Object modifiers;

    @JsonAlias({"kitchenNote", "kitchen_note", "note"})
    private String kitchenNote;
}
